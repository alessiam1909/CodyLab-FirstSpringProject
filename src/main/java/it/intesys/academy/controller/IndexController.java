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
public class IndexController {

    private final ProjectService projectService;

    public IndexController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/list-projects")
    public String index(Model model, @RequestParam String username){
        model.addAttribute("username", username);
        model.addAttribute("projects", projectService.getProjects(username));

        return "projects";
    }


}
