package com.codingtest.data.domain.question.datafetcher;

import com.codingtest.data.codegen.types.ProgrammersOrderEnum;
import com.codingtest.data.codegen.types.QuestionDto;
import com.codingtest.data.codegen.types.QuestionListDto;
import com.codingtest.data.codegen.types.QuestionListRequestDto;
import com.codingtest.data.domain.question.service.QuestionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class QuestionQueryDataFetcher {
    private final QuestionService questionService;

    @DgsQuery
    public QuestionListDto questionList(@InputArgument QuestionListRequestDto request) {
        return questionService.getQuestionList(request);
    }

    @DgsQuery
    public QuestionDto detailQuestion(@InputArgument Long id) {
        return questionService.detailQuestion(id);
    }
}
