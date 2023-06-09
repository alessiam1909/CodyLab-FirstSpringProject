package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssueController {

    private final ProjectService projectService;

    public IssueController( ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/issues-list/{username}")
    public List<IssueDTO> getIssues(@PathVariable String username, @RequestParam String projectId){
        return projectService.readIssues(Integer.parseInt(projectId), username);
    }

    @GetMapping("/single-issue/{username}")
    public IssueDTO getIssue(@PathVariable String username, @RequestParam String projectId){
        return projectService.readIssue(Integer.parseInt(projectId), username);
    }
}
