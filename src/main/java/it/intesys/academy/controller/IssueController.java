package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssueController {

    private IssueRepository issueRepository;


    public IssueController( IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping("/issues-list")
    public List<IssueDTO> getProjects(@RequestParam String username) {
        return issueRepository.GetIssues(username);
    }
}
