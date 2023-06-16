package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;
import java.util.Map;
@Repository
public class ProjectRepository {
    private  final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final  NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;
    private final IssueService issueService;

    public ProjectRepository(SettingsService settingsService, NamedParameterJdbcTemplate jdbcTemplate, IssueService issueService) {
        this.settingsService = settingsService;
        this.jdbcTemplate = jdbcTemplate;
        this.issueService = issueService;
    }

    public  List<ProjectDTO> getProjects(List<Integer> projectIds){


        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                Map.of("projectIds", projectIds),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));


        return projects;
    }
    public  ProjectDTO getProject(Integer projectId){

        ProjectDTO project = jdbcTemplate.queryForObject("SELECT id, name, description FROM Projects where id = :projectId",

                Map.of("projectId", projectId),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));
        log.info("HA FATTO LA QUERY");


        return project;
    }

    public Integer createProject(ProjectDTO projectDTO) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", projectDTO.getName())
                .addValue("description", projectDTO.getDescription()
                );
        int numberOfInsertedRows = jdbcTemplate.update("INSERT INTO Project (name, description) VALUES (:name, :description)",
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
    public void deleteProject(Integer projectId) {

        jdbcTemplate.update("delete from Projects where id = :projectId", Map.of("projectId", projectId));
        List<Integer> issuesId = issueService.readIssues()
        jdbcTemplate.update("delete from Issues where projectId = :projectId", Map.of("projectId", projectId));


    }
}
    ALTER TABLE COMMENTS
        ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY NOT NULL;

        Alter Table COMMENTS
        DROP column id ;

