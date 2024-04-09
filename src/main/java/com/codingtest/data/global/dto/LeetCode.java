package com.codingtest.data.global.dto;

import lombok.*;

import java.util.List;

public class LeetCode {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class QuestionResponse {
        private int totalQuestions;
        private int count;
        private List<Problem> problemsetQuestionList;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class Tag{
        private String name;
        private String id;
        private String slug;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class Problem {
        private String title;
        private String titleSlug;
        private List<Tag> topicTags;
        private Double acRate;
        private String difficulty;
        private String freqBar;
        private String questionFrontendId;
        private boolean isFavor;
        private boolean isPaidOnly;
        private boolean hasSolution;
        private boolean hasVideoSolution;
    }
}
