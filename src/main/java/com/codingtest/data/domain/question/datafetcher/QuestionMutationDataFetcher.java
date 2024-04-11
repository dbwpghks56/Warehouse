package com.codingtest.data.domain.question.datafetcher;

import com.codingtest.data.codegen.types.ProgrammersOrderEnum;
import com.codingtest.data.codegen.types.QuestionDto;
import com.codingtest.data.codegen.types.QuestionUpdateRequestDto;
import com.codingtest.data.domain.question.service.QuestionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class QuestionMutationDataFetcher {
    private final QuestionService questionService;

    @DgsMutation
    public String createSolvedacQuestions(@InputArgument String query, @InputArgument Integer page) {
        return questionService.createSolvedacQuestions(query, page);
    }

    @DgsMutation
    public String createProgrammersQuestions(@InputArgument Integer perPage,
                                             @InputArgument List<Integer> levels,
                                             @InputArgument List<String> languages,
                                             @InputArgument ProgrammersOrderEnum order,
                                             @InputArgument String search,
                                             @InputArgument Integer page) {
        return questionService.createProgrammersQuestions(perPage, levels, languages, order, search, page);
    }

    @DgsMutation
    public String createLeetCodeQuestions(@InputArgument Integer limit, @InputArgument List<String> tags) {
        return questionService.createLeetCodeQuestions(limit, tags);
    }

    @DgsMutation
    public QuestionDto updateQuestion(@InputArgument Long id, @InputArgument QuestionUpdateRequestDto request) {
        return questionService.questionUpdate(id, request);
    }
}
