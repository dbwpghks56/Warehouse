package com.codingtest.data.domain.question.client;

import com.codingtest.data.codegen.types.ProgrammersOrderEnum;
import com.codingtest.data.global.dto.Programmers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ProgrammersClient", url = "https://school.programmers.co.kr/api/v2/")
public interface ProgrammersClient {
    @GetMapping("school/challenges/")
    Programmers.QuestionResponse getProgrammersQuestions(
            @RequestParam int perPage,
            @RequestParam(name = "levels[]") List<Integer> levels,
            @RequestParam(name = "languages[]") List<String> languages,
            @RequestParam ProgrammersOrderEnum order,
            @RequestParam String search,
            @RequestParam int page
    );
}
