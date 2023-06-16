package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.repository.UserProjectRepository;
import org.apache.catalina.User;
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

    private final CommentRepository commentRepository;

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;

    private final UserProjectRepository userProjectRepository;


    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;



    public ProjectService(NamedParameterJdbcTemplate jdbcTemplate,UserProjectRepository userProjectRepository, SettingsService settingsService, ProjectRepository projectRepository, IssueRepository issueRepository, CommentRepository commentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
        this.userProjectRepository = userProjectRepository;
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> getProjects(String username) {

        List<ProjectDTO> projects = projectRepository.getProjects(settingsService.getUserProjects(username));
        for(ProjectDTO project:projects){
            log.info(project.getName());
        }

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

    public ProjectDTO createProject(String username, ProjectDTO projectDTO){
        Integer newProjectId = projectRepository.createProject(projectDTO);

        userProjectRepository.createUserProject(username, newProjectId);

        return projectRepository.getProject(newProjectId).get(0);
    }


    public ProjectDTO updateProject(ProjectDTO projectDTO, String userName) {
        if (!settingsService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        projectRepository.updateProject(projectDTO);


        return projectRepository.getProject(projectDTO.getId()).get(0);
    }

    public void deleteProject(Integer projectId, String username){
        userProjectRepository.deleteUser(projectId);
        projectRepository.deleteProject(projectId);
    }

    //---------------------------------------------------------------------------------------------------

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

    public IssueDTO createIssue(String username, IssueDTO issueDTO){
        int issueId = issueRepository.createIssue(issueDTO);
        return issueRepository.readIssuesForProject(issueDTO.getProjectId()).get(0);
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, String userName) {
        if (!settingsService.canThisUserReadThisProject(userName, issueDTO.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueRepository.updateIssue(issueDTO);


        return issueRepository.readIssue(issueDTO.getId());
    }


    //---------------------------------------------------------------------------------------------------
    public List<CommentDTO> getComments(Integer id, String username){
        List<ProjectDTO> projects = projectRepository.getProjects(settingsService.getUserProjects(username));
        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        if(projectIds.contains(id)) {
            return commentRepository.getComments(id);
        }
        return null;
    }

    public CommentDTO getComment(Integer id, String username){
        List<ProjectDTO> projects = projectRepository.getProjects(settingsService.getUserProjects(username));
        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        if(projectIds.contains(id)) {
            return commentRepository.getComments(id).get(0);
        }
        return null;
    }



}