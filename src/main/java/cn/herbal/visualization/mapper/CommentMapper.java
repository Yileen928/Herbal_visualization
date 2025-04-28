package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.CommentCount;
import cn.herbal.visualization.model.CommentCountByLocation;
import cn.herbal.visualization.model.CommentText;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentCount> getCommentCountsByProductNameAndYearMonth(@Param("productName") String productName);

    List<CommentCountByLocation> getCommentCountsByLocationAndProductName(@Param("productName") String productName);

    List<CommentText> getRandomCommentTextsByProductName(@Param("productName") String productName);
}
