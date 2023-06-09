package it.intesys.academy.controller;


import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {

    private final ProjectService projectService;


    public AppController( ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {
        return projectService.getProjects(userName);
    }
}