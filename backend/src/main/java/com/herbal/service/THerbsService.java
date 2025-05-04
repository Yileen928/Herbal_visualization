package com.herbal.service;

import com.herbal.domain.THerbs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs(药材详细表)】的数据库操作Service
* @createDate 2025-03-18 13:16:55
*/
public interface THerbsService extends IService<THerbs> {
    List<THerbs> getTop20Herbs();
}
