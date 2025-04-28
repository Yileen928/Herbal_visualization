package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.ProvinceCount;
import cn.herbal.visualization.model.TypeCount;
import cn.herbal.visualization.service.MedicalJobsService;
import cn.herbal.visualization.service.MedicineStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("store")
public class MedicineStoreController {
    @Autowired
    private MedicineStoreService medicineStoreService;

    @GetMapping("/city/counts")
    public List<ProvinceCount> getProvinceCounts() {
        return medicineStoreService.getProvinceCounts();
    }

    @GetMapping("/type/counts")
    public List<TypeCount> getTypeCounts() {
        return medicineStoreService.getTypeCounts();
    }
}
