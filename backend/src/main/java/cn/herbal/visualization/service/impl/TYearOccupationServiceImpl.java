package cn.herbal.visualization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.herbal.visualization.model.TYearOccupation;
import cn.herbal.visualization.service.TYearOccupationService;
import cn.herbal.visualization.mapper.TYearOccupationMapper;
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
 * @description 针对表【t_year_occupation(年份职业列表)】的数据库操作Service实现
 * @createDate 2025-04-29 15:00:39
 */
@Service
public class TYearOccupationServiceImpl extends ServiceImpl<TYearOccupationMapper, TYearOccupation>
        implements TYearOccupationService {

    private static final Logger logger = LoggerFactory.getLogger(TYearOccupationServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_ALL_YEAR_OCCUPATIONS = "allYearOccupations";
    private static final long CACHE_DURATION = 3600; // 缓存时间为1小时

    @Override
    public List<TYearOccupation> getAllyo() {
        String cacheKey = CACHE_KEY_ALL_YEAR_OCCUPATIONS;
        List<TYearOccupation> yearOccupations = null;

        try {
            // 尝试从Redis获取缓存
            yearOccupations = (List<TYearOccupation>) redisTemplate.opsForValue().get(cacheKey);

            if (yearOccupations == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                yearOccupations = this.list(); // 查询数据库
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (yearOccupations != null && !yearOccupations.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, yearOccupations, CACHE_DURATION, TimeUnit.SECONDS);
                    logger.info("Cached results for {}", cacheKey);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting all year occupations from cache", e);
            // 缓存失效时直接查询数据库
            yearOccupations = this.list();
        }

        return yearOccupations != null ? yearOccupations : Collections.emptyList();
    }
}