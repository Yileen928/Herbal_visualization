package cn.herbal.visualization.service;

import cn.herbal.visualization.model.THerbs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs(药材信息表)】的数据库操作Service
* @createDate 2025-05-01 01:27:28
*/
public interface THerbsService extends IService<THerbs> {
    IPage<THerbs> getHerbsByPage(int currentPage, int pageSize);

    List<THerbs> searchHerbsByHerbName(String herbName);

}
