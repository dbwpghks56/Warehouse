package com.codingtest.data.domain.question.client;

import com.codingtest.data.global.dto.Solvedac;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SolvdacClient", url = "https://solved.ac/api/v3/")
public interface SolvedacClient {
    @GetMapping("search/problem")
    Solvedac.QuestionResponse getSolvedacQuestions(
            @RequestParam String query,
            @RequestParam String direction,
            @RequestParam int page,
            @RequestParam String sort
    );

}
