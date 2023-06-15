package it.intesys.academy.dto;

public class CommentDTO {
    private int id;
    private String description;
    private String author;

    private int issueId;

    public int getId() {
        return id;
    }

    public CommentDTO(int id, String description, String author, int issueId) {
        this.id = id;
        this.description = description;
        this.author = author;
        this.issueId = issueId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public CommentDTO() {
    }


    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
