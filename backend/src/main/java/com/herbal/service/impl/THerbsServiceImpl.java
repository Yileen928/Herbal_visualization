package com.herbal.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.herbal.domain.THerbs;
import com.herbal.service.THerbsService;
import com.herbal.mapper.THerbsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs(药材详细表)】的数据库操作Service实现
* @createDate 2025-03-18 13:16:55
*/
@Service
public class THerbsServiceImpl extends ServiceImpl<THerbsMapper, THerbs>
    implements THerbsService{
    @Autowired
    private THerbsMapper herbsMapper;

    @Override
    public List<THerbs> getTop20Herbs() {
        Page<THerbs> page = new Page<>(1, 20);
        herbsMapper.selectPage(page, null);
        return page.getRecords();
    }
}




