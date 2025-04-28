package cn.herbal.visualization.service;

import cn.herbal.visualization.model.ProvinceCount;
import cn.herbal.visualization.model.TypeCount;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

public interface MedicineStoreService {

    @Cacheable("provinceCounts")
    List<ProvinceCount> getProvinceCounts();

    @Cacheable("typeCounts")
    List<TypeCount> getTypeCounts();
}