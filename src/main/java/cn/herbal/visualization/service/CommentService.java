package cn.herbal.visualization.service;

import cn.herbal.visualization.model.CommentCount;
import cn.herbal.visualization.model.CommentCountByLocation;
import cn.herbal.visualization.model.CommentText;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CommentService {

    @Cacheable(value = "commentCounts", key = "#productName")
    List<CommentCount> getCommentCountsByProductNameAndYearMonth(String productName);

    @Cacheable(value = "commentCountsByLocation", key = "#productName")
    List<CommentCountByLocation> getCommentCountsByLocationAndProductName(String productName);

    @Cacheable(value = "randomComments", key = "#productName")
    List<CommentText> getRandomCommentTextsByProductName(String productName);
}