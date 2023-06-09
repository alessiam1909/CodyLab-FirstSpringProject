package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssueController {


    @GetMapping("/issues")
    public List<IssueDTO> getIssues(@PathVariable Integer idProject) {
        return IssueRepository.getIssuesOfAProject(idProject);
    }
}
