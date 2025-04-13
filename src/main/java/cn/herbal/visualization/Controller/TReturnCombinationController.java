package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.THerbs;
import cn.herbal.visualization.model.TReturnCombination;
import cn.herbal.visualization.service.THerbsService;
import cn.herbal.visualization.service.TReturnCombinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/herbs")
public class TReturnCombinationController {

    @Autowired
    private THerbsService herbsService;

    @Autowired
    private TReturnCombinationService returnCombinationService;

    /**
     * 查询所有药材信息
     *
     * @return 所有药材信息列表
     */
    @GetMapping("/all")
    public List<THerbs> getAllHerbs() {
        return herbsService.list();
    }

    /**
     * 根据药材名称查询归经组合信息（联表查询）
     *
     * @param herbName 药材名称
     * @return 归经组合信息列表
     */
    @GetMapping("/search")
    public List<Map<String, Object>> getReturnCombinationsByHerbName(@RequestParam String herbName) {
        List<TReturnCombination> combinations = returnCombinationService.getReturnCombinationsByHerbName(herbName);

        Map<Long, Map<String, Object>> herbMap = new HashMap<>();

        for (TReturnCombination combination : combinations) {
            Long herbId = combination.getHerb().getHerbId();

            if (!herbMap.containsKey(herbId)) {
                Map<String, Object> herbData = new HashMap<>();
                herbData.put("herb", combination.getHerb());
                herbData.put("returns", new ArrayList<>());

                herbMap.put(herbId, herbData);
            }

            ((List<Object>) herbMap.get(herbId).get("returns")).add(combination.getRet());
        }

        return new ArrayList<>(herbMap.values());
    }
}



