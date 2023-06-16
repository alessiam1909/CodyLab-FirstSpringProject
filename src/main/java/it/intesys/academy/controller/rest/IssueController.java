package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class IssueController {
    private final IssueService issueService;
    private final IssueRepository issueRepository;
    private final SettingsService settingsService;
    private final Logger log = LoggerFactory.getLogger(IssueDTO.class);

    public IssueController(IssueService issueService, IssueRepository issueRepository, SettingsService settingsService) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
        this.settingsService = settingsService;
    }

// localhost:8080/issue/1/?userName=Mattia
    @GetMapping("/issue/{issueId}/")
    public IssueDTO getIssues(@PathVariable Integer issueId, @RequestParam String userName) {
        return issueService.readIssue(userName, issueId);
    }
    @PostMapping("/issues")
    public IssueDTO createIssue(@RequestBody IssueDTO issueDTO,
                                    @RequestParam String username) {
        if(!settingsService.getUserProjects(username).contains(issueDTO.getProjectId())){
            throw new RuntimeException("You cannot add issue to this project");
        }


        return issueService.createIssue(issueDTO);
    }
    @PutMapping("/issues/{issueId}")
    public IssueDTO updateIssue(@PathVariable int issueId, @RequestBody IssueDTO issueDTO, @RequestParam String username) {
        if (issueDTO.getId() == null) {
            throw new RuntimeException("Bad request, id must not be null when updating a project");
        }
        if (issueDTO.getId() != issueId) {
            throw new RuntimeException("Bad request, id in path and in body must be the same");
        }
        return issueService.updateIssue(issueDTO, username);
    }
    @DeleteMapping("/issues/{issueId}")
    public void deleteIssue(@PathVariable Integer issueId, @RequestParam String username) {
        issueService.deleteIssue(issueId, username);
    }

}
