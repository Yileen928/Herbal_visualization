package cn.herbal.visualization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.herbal.visualization.model.TComments;
import cn.herbal.visualization.service.TCommentsService;
import cn.herbal.visualization.mapper.TCommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ack27
 * @description 针对表【t_comments(产品评论汇总表)】的数据库操作Service实现
 * @createDate 2025-04-18 17:17:12
 */
@Service
public class TCommentsServiceImpl extends ServiceImpl<TCommentsMapper, TComments>
        implements TCommentsService {

    private static final Logger logger = LoggerFactory.getLogger(TCommentsServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_FIND_BY_PRODUCTS_NAME = "commentsByProductName:";
    private static final long CACHE_DURATION = 3600; // 缓存时间为1小时

    @Override
    public List<TComments> findByProductsName(String productsName) {
        String cacheKey = CACHE_KEY_FIND_BY_PRODUCTS_NAME + productsName;
        List<TComments> comments = (List<TComments>) redisTemplate.opsForValue().get(cacheKey);

        if (comments == null) {
            logger.info("Cache miss for {}", cacheKey);
            long startTime = System.currentTimeMillis();
            comments = baseMapper.selectByProductsName(productsName);
            long endTime = System.currentTimeMillis();
            logger.info("Database query took {} ms", endTime - startTime);
            redisTemplate.opsForValue().set(cacheKey, comments, CACHE_DURATION, TimeUnit.SECONDS);
        } else {
            logger.info("Cache hit for {}", cacheKey);
        }
        return comments;
    }

    @Override
    public List<TComments> findAll() {
        String cacheKey = "allComments";
        List<TComments> comments = (List<TComments>) redisTemplate.opsForValue().get(cacheKey);

        if (comments == null) {
            logger.info("Cache miss for {}", cacheKey);
            long startTime = System.currentTimeMillis();
            comments = list();
            long endTime = System.currentTimeMillis();
            logger.info("Database query took {} ms", endTime - startTime);
            redisTemplate.opsForValue().set(cacheKey, comments, CACHE_DURATION, TimeUnit.SECONDS);
        } else {
            logger.info("Cache hit for {}", cacheKey);
        }
        return comments;
    }
}



