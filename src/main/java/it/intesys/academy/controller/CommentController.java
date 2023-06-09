package it.intesys.academy.controller;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CommentController {


    @GetMapping("/comment/{idIssue}")
    public List<CommentDTO> getComments(@PathVariable String idIssue) {
        return CommentRepository.getCommentsOfAIssue(Integer.parseInt(idIssue));
    }
}
