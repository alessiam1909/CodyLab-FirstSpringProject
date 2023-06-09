package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.SettingsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class CommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SettingsService settingsService;


    public CommentRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }

    /**
     * SQL query read comments from issueId and return them in a list
     * @param issueId
     * @return
     */
    public List<CommentDTO> readCommentsFromIssues(int issueId) {


        List<CommentDTO> idIssue = jdbcTemplate.query("SELECT id, comment, author FROM Comments WHERE issueId in (:issueIds)",

                Map.of("issueIds", issueId),

                BeanPropertyRowMapper.newInstance(CommentDTO.class));

        return idIssue;
    }
}