package com.codingtest.data.domain.question.entity;

import com.codingtest.data.codegen.types.QuestionDto;
import com.codingtest.data.codegen.types.QuestionUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "tb_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    private String tag;

    private String source;

    private Double timeLimit;

    private Double memoryLimit;

    private Integer level;

    private Double averageTries;

    private Long totalTries;

    private Long totalPerson;

    private Double successRate;

    private Long totalSuccess;

    public QuestionDto toResponseDto() {
        return new QuestionDto.Builder()
                .id(id)
                .title(title)
                .content(content)
                .tag(tag)
                .source(source)
                .timeLimit(timeLimit)
                .memoryLimit(memoryLimit)
                .level(level)
                .averageTries(averageTries)
                .totalTries(totalTries)
                .totalPerson(totalPerson)
                .successRate(successRate)
                .totalSuccess(totalSuccess)
                .build();
    }

    public void update(QuestionUpdateRequestDto updateDto) {
        if (updateDto.getTitle() != null) {
            this.title = updateDto.getTitle();
        }
        if (updateDto.getContent() != null) {
            this.content = updateDto.getContent();
        }
        if (updateDto.getTag() != null) {
            this.tag = updateDto.getTag();
        }
        if (updateDto.getSource() != null) {
            this.source = updateDto.getSource();
        }
        if (updateDto.getTimeLimit() != null) {
            this.timeLimit = updateDto.getTimeLimit();
        }
        if (updateDto.getMemoryLimit() != null) {
            this.memoryLimit = updateDto.getMemoryLimit();
        }
        if (updateDto.getLevel() != null) {
            this.level = updateDto.getLevel();
        }
        if (updateDto.getAverageTries() != null) {
            this.averageTries = updateDto.getAverageTries();
        }
        if (updateDto.getTotalTries() != null) {
            this.totalTries = updateDto.getTotalTries();
        }
        if (updateDto.getTotalPerson() != null) {
            this.totalPerson = updateDto.getTotalPerson();
        }
        if (updateDto.getSuccessRate() != null) {
            this.successRate = updateDto.getSuccessRate();
        }
        if (updateDto.getTotalSuccess() != null) {
            this.totalSuccess = updateDto.getTotalSuccess();
        }
    }
}
