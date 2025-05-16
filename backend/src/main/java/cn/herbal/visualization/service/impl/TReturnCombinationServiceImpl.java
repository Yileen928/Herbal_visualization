package cn.herbal.visualization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.herbal.visualization.model.TReturnCombination;
import cn.herbal.visualization.service.TReturnCombinationService;
import cn.herbal.visualization.mapper.TReturnCombinationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ack27
 * @description 针对表【t_return_combination(药材—归经表)】的数据库操作Service实现
 * @createDate 2025-03-25 08:58:06
 */
@Service
public class TReturnCombinationServiceImpl extends ServiceImpl<TReturnCombinationMapper, TReturnCombination>
        implements TReturnCombinationService {

    private static final Logger logger = LoggerFactory.getLogger(TReturnCombinationServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_FIND_BY_HERB_NAME = "returnCombinationsByHerbName:";
    private static final long CACHE_DURATION = 3600; // 缓存时间为1小时

    @Override
    public List<TReturnCombination> getReturnCombinationsByHerbName(String herbName) {
        String cacheKey = CACHE_KEY_FIND_BY_HERB_NAME + herbName;
        List<TReturnCombination> returnCombinations = null;

        try {
            // 尝试从Redis获取缓存
            Object cacheValue = redisTemplate.opsForValue().get(cacheKey);

            if (cacheValue != null) {
                if (cacheValue instanceof List) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        returnCombinations = objectMapper.convertValue(cacheValue,
                                new com.fasterxml.jackson.core.type.TypeReference<List<TReturnCombination>>() {});
                        logger.info("Cache hit for {}", cacheKey);
                    } catch (IllegalArgumentException e) {
                        logger.error("Failed to convert cache value to TReturnCombination list", e);
                    }
                }
            }

            if (returnCombinations == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                returnCombinations = baseMapper.findByHerbName(herbName);
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (returnCombinations != null && !returnCombinations.isEmpty()) {
                    // 缓存前也进行排序
                    returnCombinations.sort((c1, c2) -> {
                        String name1 = c1.getHerb().getHerbName();
                        String name2 = c2.getHerb().getHerbName();

                        boolean exactMatch1 = name1.equals(herbName);
                        boolean exactMatch2 = name2.equals(herbName);

                        if (exactMatch1 && !exactMatch2) return -1;
                        if (!exactMatch1 && exactMatch2) return 1;
                        return 0;
                    });

                    redisTemplate.opsForValue().set(cacheKey, returnCombinations, CACHE_DURATION, TimeUnit.SECONDS);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting return combinations by herb name from cache", e);
            // 缓存失效时直接查询数据库
            returnCombinations = baseMapper.findByHerbName(herbName);
        }

        return returnCombinations != null ? returnCombinations : Collections.emptyList();
    }
}