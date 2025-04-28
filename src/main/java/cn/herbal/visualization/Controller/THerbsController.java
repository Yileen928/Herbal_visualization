package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.THerbs;
import cn.herbal.visualization.service.THerbsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("therbs")
public class THerbsController {

    @Autowired
    private THerbsService herbsService;

    @GetMapping("/page")
    public IPage<THerbs> getHerbsByPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "20") int pageSize) {
        return herbsService.getHerbsByPage(currentPage, pageSize);
    }
    @GetMapping("/search")
    public List<THerbs> searchHerbsByHerbName(
            @RequestParam(required = false) String herbName) {
        if (herbName == null || herbName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return herbsService.searchHerbsByHerbName(herbName);
    }
}
