package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.THerbs;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs(药材详细表)】的数据库操作Mapper
* @createDate 2025-03-25 08:57:54
* @Entity cn.herbal.visualization.model.THerbs
*/
@Mapper
public interface THerbsMapper extends BaseMapper<THerbs> {

    @Select("SELECT * FROM t_herbs WHERE herb_name = #{herbName}")
    THerbs findByHerbName(@Param("herbName") String herbName);

}




