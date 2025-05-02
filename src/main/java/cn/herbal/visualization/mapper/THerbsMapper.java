package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.THerbs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author ack27
* @description 针对表【t_herbs(药材信息表)】的数据库操作Mapper
* @createDate 2025-05-01 01:27:28
* @Entity cn.herbal.visualization.model.THerbs
*/
@Mapper
public interface THerbsMapper extends BaseMapper<THerbs> {
    @Select("SELECT * FROM t_herbs WHERE herb_name = #{herbName}")
    THerbs findByHerbName(@Param("herbName") String herbName);

}




