package cn.herbal.visualization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.herbal.visualization.model.THerbs;
import cn.herbal.visualization.service.THerbsService;
import cn.herbal.visualization.mapper.THerbsMapper;
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
 * @description 针对表【t_herbs(药材表)】的数据库操作Service实现
 * @createDate 2025-03-25 08:58:06
 */
@Service
public class THerbsServiceImpl extends ServiceImpl<THerbsMapper, THerbs>
        implements THerbsService {

    private static final Logger logger = LoggerFactory.getLogger(THerbsServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_SEARCH_BY_HERB_NAME = "herbsByHerbName:";
    private static final long CACHE_DURATION = 3600; // 缓存时间为1小时

    @Override
    public IPage<THerbs> getHerbsByPage(int currentPage, int pageSize) {
        Page<THerbs> page = new Page<>(currentPage, pageSize);
        return baseMapper.selectPage(page, null);
    }

    @Override
    public List<THerbs> searchHerbsByHerbName(String herbName) {
        String cacheKey = CACHE_KEY_SEARCH_BY_HERB_NAME + herbName;
        List<THerbs> herbsList = null;

        try {
            // 尝试从Redis获取缓存
            herbsList = (List<THerbs>) redisTemplate.opsForValue().get(cacheKey);

            if (herbsList == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                QueryWrapper<THerbs> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("herb_name", herbName); // 使用eq进行精确查询
                herbsList = baseMapper.selectList(queryWrapper);
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (herbsList != null && !herbsList.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, herbsList, CACHE_DURATION, TimeUnit.SECONDS);
                    logger.info("Cached results for {}", cacheKey);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while searching herbs by name from cache", e);
            // 缓存失效时直接查询数据库
            QueryWrapper<THerbs> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("herb_name", herbName);
            herbsList = baseMapper.selectList(queryWrapper);
        }

        return herbsList != null ? herbsList : Collections.emptyList();
    }
}