package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.HerbsWithReturns;
import cn.herbal.visualization.model.THerbsReturnSong;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs_return_song(药材络属关系表)】的数据库操作Mapper
* @createDate 2025-05-07 04:44:10
* @Entity cn.herbal.visualization.model.THerbsReturnSong
*/
public interface THerbsReturnSongMapper extends BaseMapper<THerbsReturnSong> {
    @Select("SELECT " +
            "h.herbs_id as 'herbs.herbsId', " +
            "h.herbs_name as 'herbs.herbsName', " +
            "h.medicinal_part as 'herbs.medicinalPart', " +
            "h.YinYang_attributes as 'herbs.yinyangAttributes', " +
            "h.Handsfeet_parts as 'herbs.handsfeetParts', " +
            "GROUP_CONCAT(r.genus_id) as 'tempGenusIds', " +
            "GROUP_CONCAT(r.genus_name) as 'tempGenusNames' " +
            "FROM t_herbs_song h " +
            "LEFT JOIN t_herbs_return_song hrs ON h.herbs_id = hrs.herbs_id " +
            "LEFT JOIN t_return_song r ON hrs.genus_id = r.genus_id " +
            "WHERE h.herbs_name LIKE CONCAT('%', #{herbsName}, '%') " +
            "GROUP BY h.herbs_id, h.herbs_name, h.medicinal_part, h.YinYang_attributes, h.Handsfeet_parts")
    List<HerbsWithReturns> findByHerbsName(@Param("herbsName") String herbsName);
}




