package com.codingtest.data.domain.question.service;

import com.codingtest.data.codegen.types.*;
import org.apache.catalina.core.ApplicationPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionService {
    String createSolvedacQuestions(String query, Integer page);
    String createProgrammersQuestions(Integer perPage, List<Integer> levels, List<String> languages, ProgrammersOrderEnum order, String search, Integer page);
    String createLeetCodeQuestions(Integer limit, List<String> tags);
    QuestionListDto getQuestionList(QuestionListRequestDto requestDto);
    QuestionDto questionUpdate(Long id, QuestionUpdateRequestDto updateDto);
    String uploadExcelSaveQuestions(ApplicationPart excelInput) throws IOException;
    QuestionDto detailQuestion(Long id);
}
