package com.codingtest.data.domain.question.repository.querydsl;

import com.codingtest.data.codegen.types.QuestionListDto;
import com.codingtest.data.codegen.types.QuestionListRequestDto;

public interface QueryDslQuestionRepository {
    QuestionListDto getQuestionList(QuestionListRequestDto requestDto);
}
