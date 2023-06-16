package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.CommentService;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CommentController {
    private final CommentService commentService;
    private final IssueService issueService;
    private final SettingsService settingsService;
    private  final Logger log = LoggerFactory.getLogger(ProjectService.class);

    public CommentController(CommentService commentService, IssueService issueService, SettingsService settingsService) {
        this.commentService = commentService;
        this.issueService = issueService;
        this.settingsService = settingsService;
    }


    @GetMapping("/comments/{idIssue}/")
    public List<CommentDTO> getComments(@PathVariable Integer idIssue, @RequestParam String userName) {
        return commentService.readComments(userName, idIssue);
    }
    @GetMapping("/comment/{commentId}/")
    public CommentDTO getComment(@PathVariable Integer commentId, @RequestParam String userName) {
        return commentService.readComment(userName, commentId);
    }
    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @RequestParam String userName){

        if(!settingsService.getUserProjects(userName).contains(issueService.readIssue(userName, commentDTO.getIssueId()).getProjectId())){
            throw new RuntimeException("You cannot add issue to this project");
        }

        return ResponseEntity.ok(commentService.createComment(commentDTO));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO, @RequestParam String userName, @PathVariable Integer commentId){
        if (commentDTO.getId() == null) {
            throw new RuntimeException("Bad request, id must not be null when updating a comment");
        }
        if (commentDTO.getId() != commentId) {
            throw new RuntimeException("Bad request, id in path and in body must be the same");
        }
        if(!settingsService.getUserProjects(userName).contains(issueService.readIssue(userName, commentId).getProjectId())){
            throw new RuntimeException("You cannot add comments to this project");
        }
        commentDTO.setId(commentId);

        return ResponseEntity.ok(commentService.updateComment(commentDTO))  ;
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId, @RequestParam String username) {
        if(!settingsService.getUserProjects(username).contains(issueService.readIssue(username, commentId).getProjectId())){
            throw new RuntimeException("You cannot add comments to this project");
        }
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
