package com.codingtest.data.domain.question.repository.querydsl;

import com.codingtest.data.codegen.types.QuestionListDto;
import com.codingtest.data.codegen.types.QuestionListRequestDto;
import com.codingtest.data.domain.question.entity.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
        List<Question> results;
        int total;

        results = jpaQueryFactory.selectFrom(question)
                .where(question.tag.contains(requestDto.getTag() != null ? requestDto.getTag() : "")
                        .and(question.source.contains(requestDto.getSource() != null ? requestDto.getSource() : "")
                        .and(question.title.contains(requestDto.getTitle() != null ? requestDto.getTitle() : ""))))
                .offset(offset)
                .limit(min(requestDto.getPerPage(), MAX_SIZE))
                .fetch();

        total = jpaQueryFactory.selectFrom(question)
                .where(question.tag.contains(requestDto.getTag() != null ? requestDto.getTag() : "")
                        .and(question.source.contains(requestDto.getSource() != null ? requestDto.getSource() : "")
                        .and(question.title.contains(requestDto.getTitle() != null ? requestDto.getTitle() : ""))))
                .fetch().size();

        return new QuestionListDto.Builder()
                .questions(results.stream().map(Question::toResponseDto).toList())
                .perPage(requestDto.getPerPage())
                .currentPage(requestDto.getPage())
                .total(total)
                .build();
    }
}
