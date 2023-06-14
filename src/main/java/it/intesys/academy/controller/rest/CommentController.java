package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.CommentDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.intesys.academy.service.ProjectService;

import java.util.List;


@RestController
public class CommentController {

    private final ProjectService projectService;

    public CommentController( ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/comments-list/{username}")
    public List<CommentDTO> getComments(@PathVariable String username, @RequestParam String issueId) {
        return projectService.getComments(Integer.parseInt(issueId),username);
    }

    @GetMapping("/single-comment/{username}")
    public CommentDTO getComment(@PathVariable String username, @RequestParam String issueId) {
        return projectService.getComment(Integer.parseInt(issueId),username);
    }
}
