package cn.herbal.visualization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.herbal.visualization.model.THerbsSong;
import cn.herbal.visualization.service.THerbsSongService;
import cn.herbal.visualization.mapper.THerbsSongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_herbs_song(药材药用表)】的数据库操作Service实现
* @createDate 2025-05-07 04:44:01
*/
@Service
public class THerbsSongServiceImpl extends ServiceImpl<THerbsSongMapper, THerbsSong>
    implements THerbsSongService{
}




