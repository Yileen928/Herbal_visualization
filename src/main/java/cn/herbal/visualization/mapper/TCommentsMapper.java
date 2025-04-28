package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.TComments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_comments(产品评论汇总表)】的数据库操作Mapper
* @createDate 2025-04-18 17:17:12
* @Entity cn.herbal.visualization.model.TComments
*/
public interface TCommentsMapper extends BaseMapper<TComments> {

    @Select("SELECT * FROM t_comments WHERE products_name = #{productsName}")
    List<TComments> selectByProductsName(@Param("productsName") String productsName);
}




