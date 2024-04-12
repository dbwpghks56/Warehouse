package com.codingtest.data.domain.question.repository.querydsl;

import com.codingtest.data.codegen.types.QuestionListDto;
import com.codingtest.data.codegen.types.QuestionListRequestDto;
import com.codingtest.data.domain.question.entity.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

import static com.codingtest.data.domain.question.entity.QQuestion.question;

@RequiredArgsConstructor
public class QueryDslQuestionRepositoryImpl implements QueryDslQuestionRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private static final int MAX_SIZE = 200;
    private static final int DEFAULT_PAGE = 1;

    @Override
    public QuestionListDto getQuestionList(QuestionListRequestDto requestDto) {
        long offset = (long) (max(DEFAULT_PAGE, requestDto.getPage()) - DEFAULT_PAGE) * min(requestDto.getPerPage(), MAX_SIZE);
        String tag = Optional.ofNullable(requestDto.getTag()).orElse("");
        String source = Optional.ofNullable(requestDto.getSource()).orElse("");
        String title = Optional.ofNullable(requestDto.getTitle()).orElse("");
        int total = 0;

        // JPA Query를 이용하여 검색 쿼리 실행
        List<Question> results = jpaQueryFactory.selectFrom(question)
                .where(question.tag.contains(tag)
                        .and(question.source.contains(source)
                                .and(question.title.contains(title))))
                .offset(offset)
                .limit(Math.min(requestDto.getPerPage(), MAX_SIZE))
                .fetch();

        total = jpaQueryFactory.selectFrom(question)
                .where(question.tag.contains(tag)
                        .and(question.source.contains(source)
                                .and(question.title.contains(title))))
                .fetch().size();

        return new QuestionListDto.Builder()
                .questions(results.stream().map(Question::toResponseDto).toList())
                .perPage(requestDto.getPerPage())
                .currentPage(requestDto.getPage())
                .total(total)
                .build();
    }
}
