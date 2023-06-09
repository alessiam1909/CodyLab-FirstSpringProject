package it.intesys.academy.repository;

import it.intesys.academy.controller.IssueController;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class IssueRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SettingsService settingsService;


    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }

    /**
     * SQL query read issues from  projectId and return them in a list
     * @param projectId
     * @return
     */
    public List<IssueDTO> readIssuesFromProjects(int projectId) {


        List<IssueDTO> idProject = jdbcTemplate.query("SELECT id, name, message, author FROM Issues WHERE projectId in (:projectIds)",

                Map.of("projectIds", projectId),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return idProject;

    }
}

