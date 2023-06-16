package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
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
public class IssueRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }

    public List<IssueDTO> readIssues(){
        List<IssueDTO> projects = jdbcTemplate.query("SELECT id, name, description, author, projectId FROM Issues",
                BeanPropertyRowMapper.newInstance(IssueDTO.class));
        return projects;
    }

    public List<IssueDTO> searchIssues(List<Integer> issuesIds){
        List<IssueDTO> issues = jdbcTemplate.query("SELECT id, name, description, author, projectId FROM Issues where id in (:issuesIds)",

                Map.of("issuesIds", issuesIds),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));
        return issues;
    }


    public List<IssueDTO> readIssuesForProject(Integer projectIds){
        List<IssueDTO> issues = jdbcTemplate.query("SELECT id, name, description, author, projectId FROM Issues WHERE projectId = (:projectIds)",
                Map.of("projectIds",projectIds),
                BeanPropertyRowMapper.newInstance(IssueDTO.class));
        return issues;
    }


    public IssueDTO readIssue(Integer issueId) {

        IssueDTO issues =
                jdbcTemplate.queryForObject("SELECT id, name, description, author, projectId FROM Issues WHERE id in (:issue)",

                        Map.of("issue", issueId),

                        BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return issues;
    }


    //--------------------------------------------------------------------------------------------


    public Integer createIssue(IssueDTO issueDTO) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", issueDTO.getName());
        parameterSource.addValue("description", issueDTO.getDescription());
                parameterSource.addValue("projectId", issueDTO.getProjectId());
                parameterSource.addValue("author", issueDTO.getAuthor());
        jdbcTemplate.update("INSERT INTO Issues (name, description, author, projectId) VALUES (:name, :description, :author, :projectId)",
                parameterSource, keyHolder
        );

        return keyHolder.getKey().intValue();
    }


    public void updateIssue(IssueDTO issueDTO) {
        jdbcTemplate.update("update Issues set name = :name, description = :description, author = :author, projectId = :projectId where id = :issueId",
                Map.of("name", issueDTO.getName(),
                        "description", issueDTO.getDescription(),
                        "author", issueDTO.getAuthor(),
                        "projectId", issueDTO.getProjectId(),
                        "issueId", issueDTO.getId()

                ));
    }


}
