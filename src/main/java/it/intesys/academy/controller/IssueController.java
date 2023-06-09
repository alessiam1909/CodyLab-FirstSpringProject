package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssueController {
    private final ProjectService projectService;
    private final IssueRepository issueRepository;

    public IssueController(ProjectService projectService, IssueRepository issueRepository) {
        this.projectService = projectService;
        this.issueRepository = issueRepository;
    }

// localhost:8080/issue/1/?userName=Mattia
    @GetMapping("/issue/{issueId}/")
    public IssueDTO getIssues(@PathVariable Integer issueId, @RequestParam String userName) {
        return projectService.readIssue(userName, issueId);
    }

}
