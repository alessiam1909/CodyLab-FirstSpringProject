package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssueController {
    private final IssueService issueService;
    private final IssueRepository issueRepository;

    public IssueController(IssueService issueService, IssueRepository issueRepository) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
    }

// localhost:8080/issue/1/?userName=Mattia
    @GetMapping("/issue/{issueId}/")
    public IssueDTO getIssues(@PathVariable Integer issueId, @RequestParam String userName) {
        return issueService.readIssue(userName, issueId);
    }

}
