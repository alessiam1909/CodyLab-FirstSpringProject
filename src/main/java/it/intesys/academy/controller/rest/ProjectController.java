package it.intesys.academy.controller.rest;


import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {
    private  final Logger log = LoggerFactory.getLogger(ProjectService.class);


    private final PropertyMessageService propertyMessageService;
    private final ProjectService projectService;


    public ProjectController(PropertyMessageService propertyMessageService, ProjectService projectService) {
        this.propertyMessageService = propertyMessageService;
        this.projectService = projectService;
    }



    // http://localhost:8080/list-projects?userName=sa

    @GetMapping("/list-projects")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {
        log.info(userName);
        return projectService.readProjects(userName);
    }

    // http://localhost:8080/project/1?userName=sa
    @GetMapping("/project/{projectId}/")
    public ProjectDTO getProject(@PathVariable Integer projectId, @RequestParam String userName){
        return projectService.readProject(userName, projectId);
    }


}
