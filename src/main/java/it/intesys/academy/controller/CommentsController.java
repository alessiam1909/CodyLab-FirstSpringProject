package it.intesys.academy.controller;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.CommentService;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class CommentsController {


    private final CommentService commentService;

    public CommentsController(CommentService commentService) {

        this.commentService = commentService;
    }

    @GetMapping("/comments/{idIssue}/")
    public String comments(Model model, @PathVariable Integer idIssue, @RequestParam String userName) {
        model.addAttribute("issueId", idIssue);
        model.addAttribute("username", userName);
        model.addAttribute("comments", commentService.readComments(userName, idIssue));
        return "comments";
    }
    @GetMapping("/comment/{commentId}/")
    public String comment(Model model, @PathVariable Integer commentId, @RequestParam String userName) {
        CommentDTO commentDTO = commentService.readComment(userName, commentId);
        model.addAttribute("commentId", commentId);
        model.addAttribute("issueId", commentDTO.getIssueId());
        model.addAttribute("username", userName);
        model.addAttribute("comment", commentDTO);

        return "comment";
    }
}
