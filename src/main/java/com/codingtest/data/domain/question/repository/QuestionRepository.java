package com.codingtest.data.domain.question.repository;

import com.codingtest.data.domain.question.entity.QuestionTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionTable, Long> {
}
