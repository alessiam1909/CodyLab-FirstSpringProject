package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.SettingsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class IssueRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }

    public List<IssueDTO> GetIssues(String username){

        List<Integer> userProjects = settingsService.getUserProjects(username);

        List<IssueDTO> issues = jdbcTemplate.query("SELECT id, name, message, author FROM Issues where id in (:issueIds)",
                Map.of("issueIds", userProjects),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));


        return issues;
    };



}
