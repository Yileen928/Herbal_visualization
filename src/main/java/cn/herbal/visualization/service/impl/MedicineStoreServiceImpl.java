package cn.herbal.visualization.service.impl;

import cn.herbal.visualization.mapper.MedicineStoreMapper;
import cn.herbal.visualization.model.ProvinceCount;
import cn.herbal.visualization.model.TypeCount;
import cn.herbal.visualization.service.MedicineStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MedicineStoreServiceImpl implements MedicineStoreService {
    private static final Logger logger = LoggerFactory.getLogger(MedicineStoreServiceImpl.class);

    @Autowired
    private MedicineStoreMapper medicineStoreMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_PROVINCE_COUNTS = "provinceCounts";
    private static final String CACHE_KEY_TYPE_COUNTS = "typeCounts";
    private static final long CACHE_DURATION = 3600; // 缓存时间为1小时

    @Override
    public List<ProvinceCount> getProvinceCounts() {
        String cacheKey = CACHE_KEY_PROVINCE_COUNTS;
        List<ProvinceCount> provinceCounts = (List<ProvinceCount>) redisTemplate.opsForValue().get(cacheKey);

        if (provinceCounts == null) {
            logger.info("Cache miss for {}", cacheKey);
            long startTime = System.currentTimeMillis();
            provinceCounts = medicineStoreMapper.getProvinceCounts();
            long endTime = System.currentTimeMillis();
            logger.info("Database query took {} ms", endTime - startTime);
            redisTemplate.opsForValue().set(cacheKey, provinceCounts, CACHE_DURATION, TimeUnit.SECONDS);
        } else {
            logger.info("Cache hit for {}", cacheKey);
        }
        return provinceCounts;
    }

    @Override
    public List<TypeCount> getTypeCounts() {
        String cacheKey = CACHE_KEY_TYPE_COUNTS;
        List<TypeCount> typeCounts = (List<TypeCount>) redisTemplate.opsForValue().get(cacheKey);

        if (typeCounts == null) {
            logger.info("Cache miss for {}", cacheKey);
            long startTime = System.currentTimeMillis();
            typeCounts = medicineStoreMapper.getTypeCounts();
            long endTime = System.currentTimeMillis();
            logger.info("Database query took {} ms", endTime - startTime);
            redisTemplate.opsForValue().set(cacheKey, typeCounts, CACHE_DURATION, TimeUnit.SECONDS);
        } else {
            logger.info("Cache hit for {}", cacheKey);
        }

        return typeCounts;
    }
}