package cn.herbal.visualization.service;

import cn.herbal.visualization.model.TYearOccupation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_year_occupation(年份职业列表)】的数据库操作Service
* @createDate 2025-04-29 15:00:39
*/
public interface TYearOccupationService extends IService<TYearOccupation> {
    List<TYearOccupation> getAllyo();
}
