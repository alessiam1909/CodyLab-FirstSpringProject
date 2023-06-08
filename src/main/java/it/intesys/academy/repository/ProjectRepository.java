package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.SettingsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class ProjectRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;

    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }

    public List<ProjectDTO> GetProjects(String username){

        List<Integer> userProjects = settingsService.getUserProjects(username);

        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",
                Map.of("projectIds", userProjects),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));


        return projects;
    };


    public ProjectDTO getProjectById(Integer projectId){


        ProjectDTO project = jdbcTemplate.queryForObject("SELECT id, name, description FROM Projects where id = :projectId",
                Map.of("projectId", projectId),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));

        return project;
    };




}
