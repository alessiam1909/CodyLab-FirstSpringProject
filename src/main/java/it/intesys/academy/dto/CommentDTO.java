package it.intesys.academy.dto;

import java.util.Date;

public class CommentDTO {

    private String comment;
    private Date timestamp;
    private Integer issueId;
    private String author;
    private Integer id;

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
