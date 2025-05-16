package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.HerbsWithReturns;
import cn.herbal.visualization.service.THerbsReturnSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("song")
public class THerbsReturnSongController {
    @Autowired
    private THerbsReturnSongService service;

    @GetMapping("/search")
    public List<HerbsWithReturns> getHerbsWithReturnsByHerbsName(@RequestParam String herbsName) {
        return service.getHerbsWithReturnsByHerbsName(herbsName);
    }
}
