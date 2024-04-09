package com.codingtest.data.domain.question.service;

import com.codingtest.data.codegen.types.ProgrammersOrderEnum;

import java.util.List;

public interface QuestionService {
    String createSolvedacQuestions(String query, Integer page);
    String CreateProgrammersQuestions(Integer perPage, List<Integer> levels, List<String> languages, ProgrammersOrderEnum order, String search, Integer page);
}
