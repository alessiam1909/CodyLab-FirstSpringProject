package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.SettingsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    };



    public List<ProjectDTO> getProjects(List<Integer> projectIds){
        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                Map.of("projectIds", projectIds),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));
        return projects;
    }

    public List<ProjectDTO> getProject(Integer projectIds){
        return jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                Map.of("projectIds", projectIds),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));
    }



    public Integer createProject(ProjectDTO projectDTO) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", projectDTO.getName())
                .addValue("description", projectDTO.getDescription()
                );
        jdbcTemplate.update("INSERT INTO Projects (name, description) VALUES (:name, :description)",
                parameterSource, keyHolder
        );

        return keyHolder.getKey().intValue();
    }



    public void updateProject(ProjectDTO projectDTO) {
        jdbcTemplate.update("update Projects set name = :name, description = :description where id = :projectId",
                Map.of("name", projectDTO.getName(),
                        "description", projectDTO.getDescription(),
                        "projectId", projectDTO.getId()
                ));
    }





}
