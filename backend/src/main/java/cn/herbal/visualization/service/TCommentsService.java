package cn.herbal.visualization.service;

import cn.herbal.visualization.model.TComments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_comments(产品评论汇总表)】的数据库操作Service
* @createDate 2025-04-18 17:17:12
*/
public interface TCommentsService extends IService<TComments> {
    List<TComments> findByProductsName(String productsName);

    List<TComments> findAll();
}
