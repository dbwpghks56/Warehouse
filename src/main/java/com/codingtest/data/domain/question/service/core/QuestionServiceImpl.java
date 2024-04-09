package com.codingtest.data.domain.question.service.core;

import com.codingtest.data.codegen.types.ProgrammersOrderEnum;
import com.codingtest.data.domain.question.client.ProgrammersClient;
import com.codingtest.data.domain.question.client.SolvedacClient;
import com.codingtest.data.domain.question.entity.Question;
import com.codingtest.data.domain.question.repository.QuestionRepository;
import com.codingtest.data.domain.question.service.QuestionService;
import com.codingtest.data.global.dto.Programmers;
import com.codingtest.data.global.dto.Solvedac;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final SolvedacClient solvedacClient;
    private final JdbcTemplate jdbcTemplate;
    private final ProgrammersClient programmersClient;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public String createSolvedacQuestions(String query, Integer page) {
        if (query == null) {
            query = "";
        }
        if (page == null) {
            page = 1;
        }

        Solvedac.QuestionResponse dataResponse = solvedacClient.getSolvedacQuestions(query, "asc", page, "id");

        jdbcTemplate.batchUpdate("INSERT INTO tb_question (title, content, tag, level, average_tries, total_success) VALUES (?, ?, ?, ?, ?, ?)",
                dataResponse.getItems(),
                dataResponse.getItems().size(),
                (ps, question) -> {
                    ps.setString(1, question.getTitleKo());
                    ps.setString(2, question.getTitleKo());
                    ps.setString(3, extractTagName(question.getTags()));
                    ps.setInt(4, question.getLevel());
                    ps.setDouble(5, question.getAverageTries());
                    ps.setLong(6, question.getAcceptedUserCount());
                });

        return dataResponse.getItems().size() + " 건의 데이터가 삽입되었습니다.";
    }

    @Override
    @Transactional
    public String CreateProgrammersQuestions(Integer perPage, List<Integer> levels, List<String> languages,
                                             ProgrammersOrderEnum order, String search, Integer page) {
        if (perPage == null) {
            perPage = 20;
        }

        if (page == null) {
            page = 1;
        }

        Programmers.QuestionResponse dataResponse = programmersClient.getProgrammersQuestions(perPage, levels, languages, order, search, page);

        jdbcTemplate.batchUpdate("INSERT INTO tb_question (title, content, level, success_rate, total_success) VALUES (?, ?, ?, ?, ?)",
                dataResponse.getResult(),
                dataResponse.getResult().size(),
                (ps, question) -> {
                    ps.setString(1, question.getTitle());
                    ps.setString(2, question.getPartTitle());
                    ps.setInt(3, question.getLevel());
                    ps.setInt(4, question.getAcceptanceRate());
                    ps.setDouble(5, question.getFinishedCount());
                });

        return dataResponse.getResult().size() + " 건의 데이터가 삽입되었습니다.";
    }

    private String extractTagName(List<Solvedac.Tag> tags) {
        StringBuilder tagName = new StringBuilder();

        tags.forEach(tag -> {
            tag.getDisplayNames().forEach(displayName -> {
                if ("ko".equals(displayName.getLanguage()) && displayName.getName() != null) {
                    tagName.append(displayName.getName()).append(" , ");
                }
            });
        });

        return tagName.toString();
    }
}
