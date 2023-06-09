package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProjectController {

    private final PropertyMessageService propertyMessageService;
    private final ProjectService projectService;

    public ProjectController(PropertyMessageService propertyMessageService, ProjectService projectService) {
        this.propertyMessageService = propertyMessageService;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public MessageDTO getMessage() {
        return propertyMessageService.getMessage();
    }

    // /project/int
    @GetMapping("/project/{projectId}")
    public List<ProjectDTO> getProject(@PathVariable Integer projectId) {
        return projectService.getProjectById(projectId);
    }
}


