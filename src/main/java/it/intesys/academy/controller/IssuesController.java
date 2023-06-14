package it.intesys.academy.controller;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.CommentService;
import it.intesys.academy.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class IssuesController {


    private final IssueService issueService;
    private final CommentService commentService;


    public IssuesController(IssueService issueService, CommentService commentService) {

        this.issueService = issueService;
        this.commentService = commentService;
    }
//http://localhost:8080/mvc/issue/2/?userName=Zeno
    @GetMapping("/issue/{issueId}/")
    public String issue(Model model, @PathVariable Integer issueId, @RequestParam String userName) {
        List<CommentDTO> comments = commentService.readComments(userName, issueId);
        model.addAttribute("id", issueId);
        model.addAttribute("username", userName);
        model.addAttribute("issue", issueService.readIssue(userName, issueId));
        model.addAttribute("comments", comments);


        return "issue";
    }

}

