package com.codingtest.data.domain.question.service.core;

import com.codingtest.data.domain.question.client.SolvedacClient;
import com.codingtest.data.domain.question.service.QuestionService;
import com.codingtest.data.global.dto.Solvedac;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final SolvedacClient solvedacClient;

    @Override
    public String createSolvedacQuestions() {
        Solvedac.QuestionResponse test = solvedacClient.getSolvedacQuestions("", "asc", 1, "random");

        return test.getItems().get(0).toString();
    }
}
