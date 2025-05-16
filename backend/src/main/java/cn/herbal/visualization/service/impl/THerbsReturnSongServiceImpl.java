package cn.herbal.visualization.service.impl;

import cn.herbal.visualization.model.HerbsWithReturns;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.herbal.visualization.model.THerbsReturnSong;
import cn.herbal.visualization.service.THerbsReturnSongService;
import cn.herbal.visualization.mapper.THerbsReturnSongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs_return_song(药材络属关系表)】的数据库操作Service实现
* @createDate 2025-05-07 04:44:10
*/
@Service
public class THerbsReturnSongServiceImpl extends ServiceImpl<THerbsReturnSongMapper, THerbsReturnSong>
    implements THerbsReturnSongService{
    @Autowired
    private THerbsReturnSongMapper mapper;

    public List<HerbsWithReturns> getHerbsWithReturnsByHerbsName(String herbsName) {
        return mapper.findByHerbsName(herbsName);
    }
}




