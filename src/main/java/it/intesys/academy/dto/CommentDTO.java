package it.intesys.academy.dto;

public class CommentDTO {
    private int id;
    private String comment;
    private String author;

    private int issueId;

    public int getId() {
        return id;
    }

    public CommentDTO(int id, String comment, String author, int issueId) {
        this.id = id;
        this.comment = comment;
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


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
