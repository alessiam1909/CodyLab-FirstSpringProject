package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class CommentService {

    private final ProjectRepository projectRepository;

    private final SettingsService settingsService;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    public CommentService(ProjectRepository projectRepository, SettingsService settingsService, IssueRepository issueRepository, CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.settingsService = settingsService;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
    }


    public List<CommentDTO> readComments(String username, Integer issueId){

        return commentRepository.getCommentsOfAIssue(issueId);
    }
    public CommentDTO readComment(String username, Integer commentId){
        List<Integer> projectId = settingsService.getUserProjects(username);
        CommentDTO commentDTO = commentRepository.getComment(commentId);
        IssueDTO issueDTO = issueRepository.getIssue(commentDTO.getIssueId());

        if(!projectId.contains(issueDTO.getProjectId())){
            return (new CommentDTO());
        }

        return commentDTO;
    }
}
