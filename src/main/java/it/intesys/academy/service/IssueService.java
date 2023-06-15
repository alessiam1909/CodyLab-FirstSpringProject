package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class IssueService {

    private  final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final SettingsService settingsService;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;

    private final UserProjectService userProjectService;

    public IssueService(SettingsService settingsService, IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService) {
        this.settingsService = settingsService;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
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
    public IssueDTO createIssue(IssueDTO issueDTO) {
        Integer newIssueId = issueRepository.createIssue(issueDTO);

        return issueRepository.getIssue(newIssueId);
    }
    public IssueDTO updateIssue(IssueDTO issue, String userName) {
        if (!userProjectService.canThisUserReadThisProject(userName, issue.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueRepository.updateIssue(issue);


        return issueRepository.getIssue(issue.getId());
    }
}
