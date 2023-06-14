package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class IndexController {


    private final ProjectService projectService;
    private final IssueService issueService;

    public IndexController(ProjectService projectService, IssueService issueService) {

        this.projectService = projectService;
        this.issueService = issueService;
    }

    @GetMapping("/list-projects")
    public String index(Model model, @RequestParam String userName) {

        model.addAttribute("username", userName);
        model.addAttribute("projects", projectService.readProjects(userName));

        return "index";
    }
    //http://localhost:8080/mvc/project/1?userName=sa
    @GetMapping("/project/{projectId}/")
    public String project(Model model, @PathVariable Integer projectId, @RequestParam String userName){
        ProjectDTO projectDTO = projectService.readProject(userName, projectId);
        List<IssueDTO> issues = issueService.readIssues(userName, projectId);
        model.addAttribute("issues", issues);
        model.addAttribute("username", userName);
        model.addAttribute("id", projectDTO.getId());
        model.addAttribute("project", projectDTO);


        return "project";
    }

}
