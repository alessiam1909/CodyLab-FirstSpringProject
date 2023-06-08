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

    private final ProjectRepository projectRepository;


    public ProjectController( ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/projects-list")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {
        return projectRepository.GetProjects(userName);
    }

    @GetMapping("/projects-list/{projectId}")
    public List<ProjectDTO> getProjectsById(@PathVariable String projectId) {
        return projectRepository.GetProjectsById(projectId);
    }
}
