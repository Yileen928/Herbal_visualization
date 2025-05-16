package cn.herbal.visualization.service;

import cn.herbal.visualization.model.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MedicalJobsService {
    List<CityNameCount> getCityNameCounts();

    @Cacheable("workExperienceCounts")
    List<WorkExperienceCount> getWorkExperienceCounts();

    @Cacheable("payCounts")
    List<PayCount> getPayCounts();

    @Cacheable("skillRequirements")
    List<SkillRequirement> getTop20SkillRequirements();

}
