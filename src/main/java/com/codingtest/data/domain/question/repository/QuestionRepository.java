package com.codingtest.data.domain.question.repository;

import com.codingtest.data.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
