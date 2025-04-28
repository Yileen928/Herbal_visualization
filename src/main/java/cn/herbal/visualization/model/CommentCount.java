package cn.herbal.visualization.model;

import java.io.Serializable;

public class CommentCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer commentsYear;
    private Integer commentsMonth;
    private Long commentCount;

    // Getters and Setters
    public Integer getCommentsYear() {
        return commentsYear;
    }

    public void setCommentsYear(Integer commentsYear) {
        this.commentsYear = commentsYear;
    }

    public Integer getCommentsMonth() {
        return commentsMonth;
    }

    public void setCommentsMonth(Integer commentsMonth) {
        this.commentsMonth = commentsMonth;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}