package cn.herbal.visualization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.herbal.visualization.model.TReturnCombination;
import cn.herbal.visualization.service.TReturnCombinationService;
import cn.herbal.visualization.mapper.TReturnCombinationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ack27
 * @description 针对表【t_return_combination(药材—归经表)】的数据库操作Service实现
 * @createDate 2025-03-25 08:58:06
 */
@Service
public class TReturnCombinationServiceImpl extends ServiceImpl<TReturnCombinationMapper, TReturnCombination>
        implements TReturnCombinationService {

    @Override
    public List<TReturnCombination> getReturnCombinationsByHerbName(String herbName) {
        return baseMapper.findByHerbName(herbName);
    }
}
