package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class IssuesController {


    private final IssueService issueService;

    public IssuesController(IssueService issueService) {

        this.issueService = issueService;
    }
//http://localhost:8080/mvc/issue/2/?userName=Zeno
    @GetMapping("/issue/{issueId}/")
    public String issue(Model model, @PathVariable Integer issueId, @RequestParam String userName) {
        model.addAttribute("username", userName);
        model.addAttribute("issue", issueService.readIssue(userName, issueId));


        return "issue";
    }

}

