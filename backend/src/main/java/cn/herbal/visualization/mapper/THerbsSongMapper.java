package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.THerbsSong;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs_song(药材药用表)】的数据库操作Mapper
* @createDate 2025-05-07 04:44:01
* @Entity cn.herbal.visualization.model.THerbsSong
*/
public interface THerbsSongMapper extends BaseMapper<THerbsSong> {
}




