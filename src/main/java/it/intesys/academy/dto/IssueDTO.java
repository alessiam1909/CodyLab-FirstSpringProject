package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class IssueDTO {

    private int id;

    private String name;

    private String message;

    private String author;
    private int projectId;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setComment(List<CommentDTO> comment) {
        this.comment = comment;
    }

    private List<CommentDTO> comment = new ArrayList<>();

    public List<CommentDTO> getComment() {
        return comment;
    }

    public void setComment(CommentDTO comment) {
        this.comment.add(comment);
    }

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
}
