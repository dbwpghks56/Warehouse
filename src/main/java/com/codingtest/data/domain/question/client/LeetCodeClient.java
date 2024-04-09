package com.codingtest.data.domain.question.client;

import com.codingtest.data.global.dto.LeetCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "LeetCodeClient", url = "https://alfa-leetcode-api.onrender.com/")
public interface LeetCodeClient {
    @GetMapping("problems")
    LeetCode.QuestionResponse getLeetCodeQuestions(
            @RequestParam int limit,
            @RequestParam String tags
            );
}
