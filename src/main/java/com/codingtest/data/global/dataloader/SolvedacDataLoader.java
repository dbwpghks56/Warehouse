package com.codingtest.data.global.dataloader;

import com.codingtest.data.domain.question.entity.Question;
import com.codingtest.data.domain.question.service.QuestionService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;

import java.util.List;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "solvedac")
@RequiredArgsConstructor
public class SolvedacDataLoader implements BatchLoader<Long, Question> {
    private final QuestionService questionService;
    @Override
    public CompletionStage<List<Question>> load(List<Long> keys) {
        return null;
    }
}
