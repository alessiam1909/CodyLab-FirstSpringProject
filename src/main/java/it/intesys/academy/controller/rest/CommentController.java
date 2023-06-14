package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.CommentService;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CommentController {
    private final CommentService commentService;
    private  final Logger log = LoggerFactory.getLogger(ProjectService.class);

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/comments/{idIssue}/")
    public List<CommentDTO> getComments(@PathVariable Integer idIssue, @RequestParam String userName) {
        return commentService.readComments(userName, idIssue);
    }
    @GetMapping("/comment/{commentId}/")
    public CommentDTO getComment(@PathVariable Integer commentId, @RequestParam String userName) {
        return commentService.readComment(userName, commentId);
    }
}
