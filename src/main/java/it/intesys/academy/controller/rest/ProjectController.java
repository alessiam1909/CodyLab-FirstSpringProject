package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {

        return projectService.readProjects(userName);
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDTO getProject(@PathVariable int projectId, @RequestParam String username) {

        return projectService.readProject(username, projectId);
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO,
                                    @RequestParam String username) {

        return ResponseEntity.ok(projectService.createProject(projectDTO, username));
    }
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestParam String username) {
        if (projectDTO.getId() == null) {
            throw new RuntimeException("Bad request, id must not be null when updating a project");
        }
        if (projectDTO.getId() != projectId) {
            throw new RuntimeException("Bad request, id in path and in body must be the same");
        }
        return ResponseEntity.ok(projectService.updateProject(projectDTO, username));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId, @RequestParam String username) {
        projectService.deleteProject(projectId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
