package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private  final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRepository projectRepository;

    private final SettingsService settingsService;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;

    public ProjectService(ProjectRepository projectRepository, SettingsService settingsService, IssueRepository issueRepository, CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.settingsService = settingsService;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
    }

    public List<ProjectDTO> readProjects(String username) {

        List<Integer> projectIds = settingsService.getUserProjects(username);

        List<ProjectDTO> projects = projectRepository.getProjects(projectIds);

        Map<Integer, List<IssueDTO>> issuesByProjectId = issueRepository.getAllIssues(projectIds);
        List<Integer> issueIds = new ArrayList<>();
        for(Integer key : issuesByProjectId.keySet()){
            List<IssueDTO> isList = issuesByProjectId.get(key);
            for(IssueDTO is : isList){
                issueIds.add(is.getId());
            }
        }
        Map<Integer, List<CommentDTO>> messageByIssuetId = commentRepository.getAllComments(issueIds);

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
        ProjectDTO project = projectRepository.getProject(projectId);

        if(!userProjects.contains(projectId)){
            project = null;
            return project;
        }

        for(IssueDTO issue : issueRepository.getIssuesOfAProject(projectId)){
            List<CommentDTO> messages = commentRepository.getCommentsOfAIssue(issue.getId());
            for (CommentDTO message : messages){
                issue.setComment(message);
            }
            project.addIssue(issue);
        }
        return project;
    }



}