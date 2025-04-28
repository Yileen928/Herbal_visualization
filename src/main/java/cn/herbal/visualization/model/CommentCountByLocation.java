package cn.herbal.visualization.model;

import java.io.Serializable;

public class CommentCountByLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String location;
    private Long commentCount;

    // Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
