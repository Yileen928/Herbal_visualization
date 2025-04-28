package cn.herbal.visualization.service;

import cn.herbal.visualization.model.THerbs;
import cn.herbal.visualization.model.TReturnCombination;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs(药材详细表)】的数据库操作Service
* @createDate 2025-03-25 08:57:54
*/
public interface THerbsService extends IService<THerbs> {
    IPage<THerbs> getHerbsByPage(int currentPage, int pageSize);

    List<THerbs> searchHerbsByHerbName(String herbName);
}
