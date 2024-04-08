package com.codingtest.data.domain.question.service.core;

import com.codingtest.data.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    @Override
    public String createSolvedacQuestion() {
        return "test";
    }
}
