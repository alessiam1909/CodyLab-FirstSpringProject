package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;


    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;



    public ProjectService(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService, ProjectRepository projectRepository, IssueRepository issueRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> readProjects(String username) {

        List<ProjectDTO> projects = projectRepository.getProjects(settingsService.getUserProjects(username));

        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        Map<Integer,ProjectDTO> projectById = new HashMap<>();
        for(ProjectDTO project:projects){
            projectById.put(project.getId(),project);
        }

        List<IssueDTO> issues = issueRepository.searchIssues(projectIds);

        for(IssueDTO issue:issues){
            ProjectDTO project = projectById.get(issue.getProjectId());
            if(project!=null){
                project.addIssue(issue);
            }
        }

        return projects;

    }

    public ProjectDTO getProject(Integer projectId, String username){
        List<ProjectDTO> projects = projectRepository.getProjects(settingsService.getUserProjects(username));
        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        if(projectIds.contains(projectId)) {
            return (projectRepository.getProject(projectId)).get(0);
        }
        return null;
    }

    public List<IssueDTO> readIssues(Integer id, String username){
        List<ProjectDTO> projects = projectRepository.getProjects(settingsService.getUserProjects(username));
        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        if(projectIds.contains(id)){
            return issueRepository.readIssuesForProject(id);
        }

        return null;
    }

    public IssueDTO readIssue(Integer id, String username){
        List<ProjectDTO> projects = projectRepository.getProjects(settingsService.getUserProjects(username));
        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        Map<Integer,ProjectDTO> projectById = new HashMap<>();
        for(ProjectDTO project:projects){
            projectById.put(project.getId(),project);
        }

        List<IssueDTO> issues = issueRepository.searchIssues(projectIds);

        for(IssueDTO issue:issues) {
            ProjectDTO project = projectById.get(issue.getProjectId());
            if (project != null) {
                return issueRepository.searchIssues(List.of(id)).get(0);
            }
        }

        return null;
    }



}