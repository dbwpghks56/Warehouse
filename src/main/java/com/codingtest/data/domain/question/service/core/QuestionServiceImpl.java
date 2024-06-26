package com.codingtest.data.domain.question.service.core;

import com.codingtest.data.codegen.types.*;
import com.codingtest.data.domain.question.client.LeetCodeClient;
import com.codingtest.data.domain.question.client.ProgrammersClient;
import com.codingtest.data.domain.question.client.SolvedacClient;
import com.codingtest.data.domain.question.entity.Question;
import com.codingtest.data.domain.question.repository.QuestionRepository;
import com.codingtest.data.domain.question.service.QuestionService;
import com.codingtest.data.global.dto.LeetCode;
import com.codingtest.data.global.dto.Programmers;
import com.codingtest.data.global.dto.Solvedac;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationPart;
import org.apache.poi.ss.usermodel.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final SolvedacClient solvedacClient;
    private final JdbcTemplate jdbcTemplate;
    private final ProgrammersClient programmersClient;
    private final LeetCodeClient leetCodeClient;
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

        jdbcTemplate.batchUpdate("INSERT INTO tb_question (title, content, tag, source, level, average_tries, total_success) VALUES (?, ?, ?, ?, ?, ?, ?)",
                dataResponse.getItems(),
                dataResponse.getItems().size(),
                (ps, question) -> {
                    ps.setString(1, question.getTitleKo());
                    ps.setString(2, question.getTitleKo());
                    ps.setString(3, extractSolvedTagName(question.getTags()));
                    ps.setString(4, "solved.ac");
                    ps.setInt(5, question.getLevel());
                    ps.setDouble(6, question.getAverageTries());
                    ps.setLong(7, question.getAcceptedUserCount());
                });

        return dataResponse.getItems().size() + " 건의 데이터가 삽입되었습니다.";
    }

    @Override
    @Transactional
    public String createProgrammersQuestions(Integer perPage, List<Integer> levels, List<String> languages,
                                             ProgrammersOrderEnum order, String search, Integer page) {
        if (perPage == null) {
            perPage = 20;
        }

        if (page == null) {
            page = 1;
        }

        Programmers.QuestionResponse dataResponse = programmersClient.getProgrammersQuestions(perPage, levels, languages, order, search, page);

        jdbcTemplate.batchUpdate("INSERT INTO tb_question (title, content, tag, level, source, success_rate, total_success) VALUES (?, ?, ?, ?, ?, ?, ?)",
                dataResponse.getResult(),
                dataResponse.getResult().size(),
                (ps, question) -> {
                    ps.setString(1, question.getTitle());
                    ps.setString(2, question.getPartTitle());
                    ps.setString(3, "");
                    ps.setInt(4, question.getLevel());
                    ps.setString(5, "programmers");
                    ps.setInt(6, question.getAcceptanceRate());
                    ps.setDouble(7, question.getFinishedCount());
                });

        return dataResponse.getResult().size() + " 건의 데이터가 삽입되었습니다.";
    }

    @Override
    @Transactional
    public String createLeetCodeQuestions(Integer limit, List<String> tags) {
        if (limit == null) {
            limit = 20;
        }
        String tag = "";

        if (tags != null) {
            tag = String.join("+", tags);
        }

        LeetCode.QuestionResponse dataResponse = leetCodeClient.getLeetCodeQuestions(limit, tag);

        jdbcTemplate.batchUpdate("INSERT INTO tb_question (title, content, tag, source, level, success_rate) VALUES (?, ?, ?, ?, ?, ?)",
                dataResponse.getProblemsetQuestionList(),
                dataResponse.getProblemsetQuestionList().size(),
                (ps, question) -> {
                    ps.setString(1, question.getTitle());
                    ps.setString(2, question.getTitleSlug());
                    ps.setString(3, extractLeetCodeTagName(question.getTopicTags()));
                    ps.setString(4, "leetcode");
                    ps.setInt(5, extractLevel(question.getDifficulty()));
                    ps.setDouble(6, question.getAcRate());
                });


        return dataResponse.getProblemsetQuestionList().size() + " 건의 데이터가 삽입되었습니다.";
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionListDto getQuestionList(QuestionListRequestDto requestDto) {
        if (requestDto == null) {
            requestDto = new QuestionListRequestDto.Builder().page(1).perPage(20).build();
        }
        else {
            if (requestDto.getPage() == null) {
                requestDto.setPage(1);
            }

            if (requestDto.getPerPage() == null) {
                requestDto.setPerPage(20);
            }
        }

        return questionRepository.getQuestionList(requestDto);
    }

    @Override
    public QuestionDto questionUpdate(Long id, QuestionUpdateRequestDto updateDto) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 데이터가 없습니다."));

        if (updateDto != null) {
            question.update(updateDto);
        }
        else {
            throw new IllegalArgumentException("수정할 데이터가 없습니다.");
        }

        return question.toResponseDto();
    }

    @Override
    public String uploadExcelSaveQuestions(ApplicationPart excelInput) throws IOException {
        InputStream inputStream = excelInput.getInputStream();

        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        List<Question> questions = new ArrayList<>();

        int rowIndex = 0;
        for (Row row : sheet) {
            if (rowIndex == 0) {
                // 첫 번째 행인 경우 건너뜀
                rowIndex++;
                continue;
            }

            // 각 셀을 반복하여 데이터 읽기
            Question question = Question.builder()
                    .problemId(Long.parseLong(extractCell(row.getCell(0))))
                    .title(extractCell(row.getCell(1)))
                    .content(extractCell(row.getCell(2)))
                    .timeLimit(extractCell(row.getCell(3)))
                    .memoryLimit(extractCell(row.getCell(4)))
                    .totalTries(Long.parseLong(extractCell(row.getCell(5))))
                    .totalSuccess(Long.parseLong(extractCell(row.getCell(6))))
                    .totalPerson(Long.parseLong(extractCell(row.getCell(7))))
                    .successRate(extractCell(row.getCell(8)))
                    .level(Integer.parseInt(extractCell(row.getCell(9))))
                    .tag(extractCell(row.getCell(10)))
                    .source(extractCell(row.getCell(11)))
                    .build();

            questions.add(question);
        }

        jdbcTemplate.batchUpdate("INSERT INTO tb_question " +
                        "(problem_id, title, content, time_limit, memory_limit, total_tries, total_success, total_person, success_rate, level, tag, source)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                questions,
                questions.size(),
                (ps, question) -> {
                    ps.setLong(1, question.getProblemId());
                    ps.setString(2, question.getTitle());
                    ps.setString(3, question.getContent());
                    ps.setString(4, question.getTimeLimit());
                    ps.setString(5, question.getMemoryLimit());
                    ps.setLong(6, question.getTotalTries());
                    ps.setLong(7, question.getTotalSuccess());
                    ps.setLong(8, question.getTotalPerson());
                    ps.setString(9, question.getSuccessRate());
                    ps.setInt(10, question.getLevel());
                    ps.setString(11, question.getTag());
                    ps.setString(12, question.getSource());
                });

        workbook.close();
        inputStream.close();

        return questions.size() + "개의 데이터가 성공적으로 추가되었습니다.";
    }

    @Override
    public QuestionDto detailQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 데이터가 없습니다.")).toResponseDto();
    }

    private String extractSolvedTagName(List<Solvedac.Tag> tags) {
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

    private String extractLeetCodeTagName(List<LeetCode.Tag> tags) {
        StringBuilder tagName = new StringBuilder();

        tags.forEach(tag -> {
            tagName.append(tag.getName()).append(" , ");
        });

        return tagName.toString();
    }

    private int extractLevel(String difficulty) {
        return switch (difficulty) {
            case "Easy" -> 1;
            case "Medium" -> 2;
            case "Hard" -> 3;
            default -> 0;
        };
    }

    private String extractCell(Cell cell) {
        return cell != null ? cell.getStringCellValue() : "";
    }
}
