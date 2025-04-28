package cn.herbal.visualization.model;

import java.io.Serializable;

public class CommentText implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commentText;

    // Getters and Setters
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}