package com.codingtest.data.domain.question.service;

import com.codingtest.data.codegen.types.ProgrammersOrderEnum;
import com.codingtest.data.codegen.types.QuestionListDto;
import com.codingtest.data.codegen.types.QuestionListRequestDto;

import java.util.List;

public interface QuestionService {
    String createSolvedacQuestions(String query, Integer page);
    String createProgrammersQuestions(Integer perPage, List<Integer> levels, List<String> languages, ProgrammersOrderEnum order, String search, Integer page);
    String createLeetCodeQuestions(Integer limit, List<String> tags);

    QuestionListDto getQuestionList(QuestionListRequestDto requestDto);
}
