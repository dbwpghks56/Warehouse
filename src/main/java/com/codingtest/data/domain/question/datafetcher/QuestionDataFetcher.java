package com.codingtest.data.domain.question.datafetcher;

import com.codingtest.data.domain.question.service.QuestionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class QuestionDataFetcher {
    private final QuestionService questionService;

    @DgsMutation
    public String createSolvedacQuestion() {
        return questionService.createSolvedacQuestion();
    }
}
