package it.intesys.academy.controller;

import org.springframework.ui.Model;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
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


    @GetMapping("/list-issues/{projectId}")
    public String issues(Model model,  @PathVariable Integer projectId, @RequestParam String username){
        model.addAttribute("username", username);
        model.addAttribute("projectId", projectId);
        model.addAttribute("issues", projectService.readIssues(projectId, username));

        return "issues";
    }

    @GetMapping("/list-comments/{issueId}")
    public String comments(Model model,  @PathVariable Integer issueId, @RequestParam String username){
        model.addAttribute("username", username);
        model.addAttribute("issueId", issueId);
        model.addAttribute("comments", projectService.getComments(issueId, username));

        return "comments";
    }

}
