package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class ProjectRepository {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private static NamedParameterJdbcTemplate jdbcTemplate;

    private static SettingsService settingsService;

    public ProjectRepository(SettingsService settingsService, NamedParameterJdbcTemplate jdbcTemplate) {
        this.settingsService = settingsService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static List<ProjectDTO> getProjects(String userName){
        List<Integer> projectIds = settingsService.getUserProjects(userName);


        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                Map.of("projectIds", projectIds),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));


        return projects;
    }
    public static ProjectDTO getProject(Integer projectId){

        ProjectDTO project = jdbcTemplate.queryForObject("SELECT id, name, description FROM Projects where id = :projectId",

                Map.of("projectId", projectId),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));

        return project;
    }
}
