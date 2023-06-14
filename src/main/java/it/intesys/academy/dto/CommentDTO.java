package it.intesys.academy.dto;

import java.util.Date;

public class CommentDTO {

    private String comment;
    private Date timestamp;
    private int issueId;

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
