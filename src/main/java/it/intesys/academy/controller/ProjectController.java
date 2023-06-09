package it.intesys.academy.controller;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/single-project/{username}")
    public ProjectDTO getProject(@PathVariable String username,@RequestParam String projectId) {
        return projectService.getProject(Integer.parseInt(projectId),username);
    }
}
