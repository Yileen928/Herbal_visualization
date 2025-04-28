package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.ProvinceCount;
import cn.herbal.visualization.model.TypeCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface MedicineStoreMapper extends BaseMapper<ProvinceCount> {

    List<ProvinceCount> getProvinceCounts();

    List<TypeCount> getTypeCounts();
}