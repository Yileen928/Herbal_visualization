package cn.herbal.visualization.service;

import cn.herbal.visualization.model.HerbsWithReturns;
import cn.herbal.visualization.model.THerbsReturnSong;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs_return_song(药材络属关系表)】的数据库操作Service
* @createDate 2025-05-07 04:44:10
*/
public interface THerbsReturnSongService extends IService<THerbsReturnSong> {
    public List<HerbsWithReturns> getHerbsWithReturnsByHerbsName(String herbsName);
}
