package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class IssuesController {


    private final ProjectService projectService;

    public IssuesController(ProjectService projectService) {

        this.projectService = projectService;
    }

    @GetMapping("/issue/{issueId}/")
    public String issue(Model model, @PathVariable Integer issueId, @RequestParam String userName) {
        model.addAttribute("username", userName);
        model.addAttribute("issue", projectService.readIssue(userName, issueId));


        return "issue";
    }

}

