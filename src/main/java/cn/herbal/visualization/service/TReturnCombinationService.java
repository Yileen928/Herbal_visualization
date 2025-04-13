package cn.herbal.visualization.service;

import cn.herbal.visualization.model.TReturnCombination;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ack27
 * @description 针对表【t_return_combination(药材—归经表)】的数据库操作Service
 * @createDate 2025-03-25 08:58:06
 */
public interface TReturnCombinationService extends IService<TReturnCombination> {
    List<TReturnCombination> getReturnCombinationsByHerbName(String herbName);
}
