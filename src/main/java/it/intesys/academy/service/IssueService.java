package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class IssueService {

    private final ProjectRepository projectRepository;

    private final SettingsService settingsService;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;

    public IssueService(ProjectRepository projectRepository, SettingsService settingsService, IssueRepository issueRepository, CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.settingsService = settingsService;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
    }


    public List<IssueDTO> readIssues(String username, Integer projectId){
        List<IssueDTO> issues = issueRepository.getIssuesOfAProject(projectId);

        List<Integer> issueIds = new ArrayList<>();
        for(IssueDTO issue : issues){
            issueIds.add(issue.getId());
        }
        Map<Integer, List<CommentDTO>> messageByIssueId = commentRepository.getAllComments(issueIds);

        for (IssueDTO issue : issues){
            List<CommentDTO> messageDTOS = messageByIssueId.get(issue.getId());
            for (CommentDTO message : messageDTOS) {
                issue.setComment(message);
            }
        }
        return issues;
    }

    public IssueDTO readIssue(String username, Integer issueId){
        List<Integer> projectId = settingsService.getUserProjects(username);
        IssueDTO issueDTO = issueRepository.getIssue(issueId);

        if(!projectId.contains(issueDTO.getProjectId())){
            return (new IssueDTO());
        }

        List<CommentDTO> messageDTOS = commentRepository.getCommentsOfAIssue(issueId);
        for (CommentDTO message : messageDTOS) {
            issueDTO.setComment(message);
        }
        return issueDTO;
    }
}
