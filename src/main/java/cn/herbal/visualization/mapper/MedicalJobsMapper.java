package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface MedicalJobsMapper {
    List<CityNameCount> getCityNameCounts();

    List<WorkExperienceCount> getWorkExperienceCounts();

    List<PayCount> getPayCounts();

    List<SkillRequirement> getTop20SkillRequirements();
}
