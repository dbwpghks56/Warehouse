package com.codingtest.data.domain.question.entity;

import com.codingtest.data.codegen.types.QuestionDto;
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

    @Column(nullable = false)
    private String tag;

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
}
