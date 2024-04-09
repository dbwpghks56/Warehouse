package com.codingtest.data.domain.question.repository;

import com.codingtest.data.domain.question.entity.Question;
import com.codingtest.data.domain.question.repository.querydsl.QueryDslQuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QueryDslQuestionRepository {
}
