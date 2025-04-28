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
        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);
        List<TReturnCombination> returnCombinations = null;

        if (cacheValue != null) {
            if (cacheValue instanceof List) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    returnCombinations = objectMapper.convertValue(cacheValue, new com.fasterxml.jackson.core.type.TypeReference<List<TReturnCombination>>() {});
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
            redisTemplate.opsForValue().set(cacheKey, returnCombinations, CACHE_DURATION, TimeUnit.SECONDS);
        }

        return returnCombinations;
    }
}