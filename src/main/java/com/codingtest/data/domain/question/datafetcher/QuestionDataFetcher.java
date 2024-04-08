package com.codingtest.data.domain.question.datafetcher;

import com.codingtest.data.domain.question.service.QuestionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class QuestionDataFetcher {
    private final QuestionService questionService;

    @DgsMutation
    public String createSolvedacQuestions(@InputArgument String query, @InputArgument Integer page) {
        return questionService.createSolvedacQuestions(query, page);
    }
}
