package com.codingtest.data.global.dto;

import lombok.*;

import java.util.List;

public class Solvedac {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class QuestionResponse {
        private int count;
        private List<Problem> items;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class Tag{
        private String key;
        private boolean isMeta;
        private int bojTagId;
        private int problemCount;
        private List<DisplayName> displayNames;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class DisplayName{
        private String language;
        private String name;
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class Title {
        private String language;
        private String languageDisplayName;
        private String title;
        private boolean isOriginal;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class Problem {
        private long problemId;
        private String titleKo;
        private List<Title> titles;
        private String language;
        private String languageDisplayName;
        private String title;
        private boolean isOriginal;
        private boolean isSolvable;
        private boolean isPartial;
        private long acceptedUserCount;
        private int level;
        private long votedUserCount;
        private boolean sprout;
        private boolean givesNoRating;
        private boolean isLevelLocked;
        private double averageTries;
        private boolean official;
        private List<Tag> tags;
    }
}
