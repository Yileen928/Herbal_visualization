package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.TYearOccupation;
import cn.herbal.visualization.service.TYearOccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/yearOcc")
public class YearOccupationController {

    @Autowired
    private TYearOccupationService yearOccupationService;

    @GetMapping("/all")
    public List<TYearOccupation> getAllyo() {
        System.out.println(yearOccupationService.getAllyo());
        return yearOccupationService.getAllyo();
    }

}
