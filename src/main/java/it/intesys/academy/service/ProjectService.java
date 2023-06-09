package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);


    private final SettingsService settingsService;

    public ProjectService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public List<ProjectDTO> readProjects(String username) {

        List<ProjectDTO> projects = ProjectRepository.getProjects(username);
        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();
        Map<Integer, List<IssueDTO>> issuesByProjectId = IssueRepository.getAllIssues(projectIds);
        List<Integer> issueIds = new ArrayList<>();
        for(Integer key : issuesByProjectId.keySet()){
            List<IssueDTO> isList = issuesByProjectId.get(key);
            for(IssueDTO is : isList){
                issueIds.add(is.getId());
            }
        }
        Map<Integer, List<CommentDTO>> messageByIssuetId = CommentRepository.getAllComments(issueIds);

        for (ProjectDTO dto : projects) {
            List<IssueDTO> issueDTOS = issuesByProjectId.get(dto.getId());
            for (IssueDTO issue : issueDTOS){
                List<CommentDTO> messageDTOS = messageByIssuetId.get(issue.getId());
                for (CommentDTO message : messageDTOS) {
                    issue.setComment(message);
                }
                dto.addIssue(issue);
            }
        }


        return projects;

    }
    public ProjectDTO readProject(String username, Integer projectId){
        List<Integer> userProjects = settingsService.getUserProjects(username);
        ProjectDTO project = ProjectRepository.getProject(projectId);

        if(!userProjects.contains(projectId)){
            project = null;
            return project;
        }
        for(IssueDTO issue : IssueRepository.getIssuesOfAProject(projectId)){
            List<CommentDTO> messages = CommentRepository.getCommentsOfAIssue(issue.getId());
            for (CommentDTO message : messages){
                issue.setComment(message);
            }
            project.addIssue(issue);
        }
        return project;
    }


}