package it.intesys.academy.controller;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CommentController {
    private final ProjectService projectService;

    public CommentController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/comment/{idIssue}/")
    public List<CommentDTO> getComments(@PathVariable Integer idIssue, @RequestParam String userName) {
        return projectService.readComments(userName, idIssue);
    }
}
