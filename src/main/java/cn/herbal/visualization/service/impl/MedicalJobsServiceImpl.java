package cn.herbal.visualization.service.impl;

import cn.herbal.visualization.mapper.MedicalJobsMapper;
import cn.herbal.visualization.model.*;
import cn.herbal.visualization.service.MedicalJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MedicalJobsServiceImpl implements MedicalJobsService {

    private static final Logger logger = LoggerFactory.getLogger(MedicalJobsServiceImpl.class);

    @Autowired
    private MedicalJobsMapper cityMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_CITY_NAME_COUNTS = "cityNameCounts";
    private static final String CACHE_KEY_WORK_EXPERIENCE_COUNTS = "workExperienceCounts";
    private static final String CACHE_KEY_PAY_COUNTS = "payCounts";
    private static final String CACHE_KEY_SKILL_REQUIREMENTS = "skillRequirements";
    private static final long CACHE_DURATION = 3600; // 缓存时间为1小时

    @Override
    public List<CityNameCount> getCityNameCounts() {
        String cacheKey = CACHE_KEY_CITY_NAME_COUNTS;
        List<CityNameCount> cityNameCounts = null;

        try {
            // 尝试从Redis获取缓存
            cityNameCounts = (List<CityNameCount>) redisTemplate.opsForValue().get(cacheKey);

            if (cityNameCounts == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                cityNameCounts = cityMapper.getCityNameCounts();
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (cityNameCounts != null && !cityNameCounts.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, cityNameCounts, CACHE_DURATION, TimeUnit.SECONDS);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting city name counts from cache", e);
            // 缓存失效时直接查询数据库
            cityNameCounts = cityMapper.getCityNameCounts();
        }

        return cityNameCounts != null ? cityNameCounts : Collections.emptyList();
    }

    @Override
    public List<WorkExperienceCount> getWorkExperienceCounts() {
        String cacheKey = CACHE_KEY_WORK_EXPERIENCE_COUNTS;
        List<WorkExperienceCount> workExperienceCounts = null;

        try {
            // 尝试从Redis获取缓存
            workExperienceCounts = (List<WorkExperienceCount>) redisTemplate.opsForValue().get(cacheKey);

            if (workExperienceCounts == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                workExperienceCounts = cityMapper.getWorkExperienceCounts();
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (workExperienceCounts != null && !workExperienceCounts.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, workExperienceCounts, CACHE_DURATION, TimeUnit.SECONDS);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting work experience counts from cache", e);
            // 缓存失效时直接查询数据库
            workExperienceCounts = cityMapper.getWorkExperienceCounts();
        }

        return workExperienceCounts != null ? workExperienceCounts : Collections.emptyList();
    }

    @Override
    public List<PayCount> getPayCounts() {
        String cacheKey = CACHE_KEY_PAY_COUNTS;
        List<PayCount> payCounts = null;

        try {
            // 尝试从Redis获取缓存
            payCounts = (List<PayCount>) redisTemplate.opsForValue().get(cacheKey);

            if (payCounts == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                payCounts = cityMapper.getPayCounts();
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (payCounts != null && !payCounts.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, payCounts, CACHE_DURATION, TimeUnit.SECONDS);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting pay counts from cache", e);
            // 缓存失效时直接查询数据库
            payCounts = cityMapper.getPayCounts();
        }

        return payCounts != null ? payCounts : Collections.emptyList();
    }

    @Override
    public List<SkillRequirement> getTop20SkillRequirements() {
        String cacheKey = CACHE_KEY_SKILL_REQUIREMENTS;
        List<SkillRequirement> skillRequirements = null;

        try {
            // 尝试从Redis获取缓存
            skillRequirements = (List<SkillRequirement>) redisTemplate.opsForValue().get(cacheKey);

            if (skillRequirements == null) {
                logger.info("Cache miss for {}", cacheKey);
                long startTime = System.currentTimeMillis();
                skillRequirements = cityMapper.getTop20SkillRequirements();
                long endTime = System.currentTimeMillis();
                logger.info("Database query took {} ms", endTime - startTime);

                // 只有查询结果不为空时才缓存
                if (skillRequirements != null && !skillRequirements.isEmpty()) {
                    redisTemplate.opsForValue().set(cacheKey, skillRequirements, CACHE_DURATION, TimeUnit.SECONDS);
                }
            } else {
                logger.info("Cache hit for {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting skill requirements from cache", e);
            // 缓存失效时直接查询数据库
            skillRequirements = cityMapper.getTop20SkillRequirements();
        }

        return skillRequirements != null ? skillRequirements : Collections.emptyList();
    }
}