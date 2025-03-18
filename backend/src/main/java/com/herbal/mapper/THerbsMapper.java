package com.herbal.mapper;

import com.herbal.domain.THerbs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ack27
* @description 针对表【t_herbs(药材详细表)】的数据库操作Mapper
* @createDate 2025-03-18 13:16:55
* @Entity com.herbal.domain.THerbs
*/
@Mapper
public interface THerbsMapper extends BaseMapper<THerbs> {

}




