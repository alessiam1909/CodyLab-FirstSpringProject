package it.intesys.academy.controller;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.ProjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    private ProjectRepository projectRepository;


    public ProjectController( ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/projects-list")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {
        return projectRepository.GetProjects(userName);
    }

    @GetMapping("/single-project/{projectId}")
    public ProjectDTO getProjectsById(@PathVariable String projectId) {
        return projectRepository.getProjectById(Integer.parseInt(projectId));
    }
}
