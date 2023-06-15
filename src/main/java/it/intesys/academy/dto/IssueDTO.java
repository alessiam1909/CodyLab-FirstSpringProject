package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class IssueDTO {

    private int id;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public IssueDTO() {
    }

    public IssueDTO(int id, int projectId, String name, String message, String author) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.message = message;
        this.author = author;
    }

    private int projectId;

    private String name;

    private String message;

    private String author;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    private ArrayList<CommentDTO> comments = new ArrayList<>();

    public void addComment(CommentDTO comment) {
        comments.add(comment);
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

}
