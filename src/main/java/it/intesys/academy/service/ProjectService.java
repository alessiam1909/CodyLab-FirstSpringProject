package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final SettingsService settingsService;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final UserProjectService userProjectService;

    public ProjectService(ProjectRepository projectRepository, SettingsService settingsService, IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService) {
        this.projectRepository = projectRepository;
        this.settingsService = settingsService;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
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

    public ProjectDTO createProject(ProjectDTO projectDTO, String username) {

        Integer newProjectId = projectRepository.createProject(projectDTO);

        userProjectService.associateUserToProject(username, newProjectId);

        return projectRepository.getProject(newProjectId);
    }
    public ProjectDTO updateProject(ProjectDTO projectDTO, String userName) {
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        projectRepository.updateProject(projectDTO);


        return projectRepository.getProject(projectDTO.getId());
    }



}