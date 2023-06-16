package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/projects")
    public ProjectDTO createProject(@RequestParam String username, @RequestBody ProjectDTO projectDTO){
        return projectService.createProject(username, projectDTO);
    }


    @PutMapping("/projects/{projectId}")
    public ProjectDTO updateProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestParam String username){
        ProjectDTO updatedProject = projectService.updateProject(projectDTO, username);
        return  updatedProject;
    }

    @DeleteMapping("/projects/{projectId}")
    public void deleteProject(@PathVariable Integer projectId, @RequestParam String username){
        projectService.deleteProject(projectId, username);
    }
}
