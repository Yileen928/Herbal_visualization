package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.*;
import cn.herbal.visualization.service.MedicalJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medical")
public class MedicalJobsController {
    @Autowired
    private MedicalJobsService cityService;

    @GetMapping("/cities/counts")
    public List<CityNameCount> getCityNameCounts() {
        return cityService.getCityNameCounts();
    }

    @GetMapping("/pay/counts")
    public List<PayCount> getPayCounts() {
        return cityService.getPayCounts();
    }

    @GetMapping("/experience/counts")
    public List<WorkExperienceCount> getWorkExperienceCounts() {
        return cityService.getWorkExperienceCounts();
    }

    @GetMapping("skills/top20")
    public List<SkillRequirement> getTop20SkillRequirements() {
        return cityService.getTop20SkillRequirements();
    }
}
