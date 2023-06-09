package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
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
public class ProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;


    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }

    /**
     * SQL query read project(s?) from  projectId and return them in a list
     * @param projectId
     * @return
     */
    public List<ProjectDTO> readProjectById(int projectId) {
        List<ProjectDTO> project = jdbcTemplate.query("SELECT id, name, description FROM Projects WHERE id in (:projectIds)",
                Map.of("projectIds", projectId),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));


        return project;
    }
}