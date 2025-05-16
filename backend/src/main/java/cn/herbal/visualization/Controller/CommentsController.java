package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.TComments;
import cn.herbal.visualization.service.TCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private TCommentsService commentService;

    @GetMapping("/all")
    public List<TComments> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/product/{productsName}")
    public List<TComments> getCommentsByProductName(@PathVariable String productsName) {
        return commentService.findByProductsName(productsName);
    }
}
