package com.codingtest.data.global.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class Programmers {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class QuestionResponse {
        private int page;
        private int perPage;
        private int totalPages;
        private int totalEntries;
        private List<String> languages;
        private List<Problem> result;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class Problem {
        private int id;
        private String title;
        private String partTitle;
        private int level;
        private int finishedCount;
        private int acceptanceRate;
        private String status;
    }
}
