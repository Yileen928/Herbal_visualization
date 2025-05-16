package cn.herbal.visualization.service.impl;

import cn.herbal.visualization.mapper.CommentMapper;
import cn.herbal.visualization.model.CommentCount;
import cn.herbal.visualization.model.CommentCountByLocation;
import cn.herbal.visualization.model.CommentText;
import cn.herbal.visualization.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_COMMENT_COUNTS = "commentCounts";
    private static final String CACHE_KEY_COMMENT_COUNTS_BY_LOCATION = "commentCountsByLocation";
    private static final String CACHE_KEY_RANDOM_COMMENTS = "randomComments";
    private static final long CACHE_DURATION = 3600; // 缓存时间为1小时

    @Override
    public List<CommentCount> getCommentCountsByProductNameAndYearMonth(String productName) {
        String cacheKey = buildCacheKey(CACHE_KEY_COMMENT_COUNTS, productName);
        List<CommentCount> commentCounts = null;

        try {
            // 尝试从Redis获取缓存
            commentCounts = (List<CommentCount>) redisTemplate.opsForValue().get(cacheKey);

            if (commentCounts == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                commentCounts = commentMapper.getCommentCountsByProductNameAndYearMonth(productName);
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (commentCounts != null && !commentCounts.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, commentCounts, CACHE_DURATION, TimeUnit.SECONDS);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting comment counts from cache for product: {}", productName, e);
            // 缓存失效时直接查询数据库
            commentCounts = commentMapper.getCommentCountsByProductNameAndYearMonth(productName);
        }

        return commentCounts != null ? commentCounts : Collections.emptyList();
    }

    @Override
    public List<CommentCountByLocation> getCommentCountsByLocationAndProductName(String productName) {
        String cacheKey = buildCacheKey(CACHE_KEY_COMMENT_COUNTS_BY_LOCATION, productName);
        List<CommentCountByLocation> commentCounts = null;

        try {
            // 尝试从Redis获取缓存
            commentCounts = (List<CommentCountByLocation>) redisTemplate.opsForValue().get(cacheKey);

            if (commentCounts == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                commentCounts = commentMapper.getCommentCountsByLocationAndProductName(productName);
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (commentCounts != null && !commentCounts.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, commentCounts, CACHE_DURATION, TimeUnit.SECONDS);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting comment counts by location from cache for product: {}", productName, e);
            // 缓存失效时直接查询数据库
            commentCounts = commentMapper.getCommentCountsByLocationAndProductName(productName);
        }

        return commentCounts != null ? commentCounts : Collections.emptyList();
    }

    @Override
    public List<CommentText> getRandomCommentTextsByProductName(String productName) {
        String cacheKey = buildCacheKey(CACHE_KEY_RANDOM_COMMENTS, productName);
        List<CommentText> randomComments = null;

        try {
            // 尝试从Redis获取缓存
            randomComments = (List<CommentText>) redisTemplate.opsForValue().get(cacheKey);

            if (randomComments == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                randomComments = commentMapper.getRandomCommentTextsByProductName(productName);
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (randomComments != null && !randomComments.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, randomComments, CACHE_DURATION, TimeUnit.SECONDS);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting random comments from cache for product: {}", productName, e);
            // 缓存失效时直接查询数据库
            randomComments = commentMapper.getRandomCommentTextsByProductName(productName);
        }

        return randomComments != null ? randomComments : Collections.emptyList();
    }

    /**
     * 构建缓存键
     * @param prefix 缓存键前缀
     * @param productName 产品名称
     * @return 完整的缓存键
     */
    private String buildCacheKey(String prefix, String productName) {
        return prefix + ":" + productName;
    }
}