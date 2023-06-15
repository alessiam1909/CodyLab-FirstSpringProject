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


}
