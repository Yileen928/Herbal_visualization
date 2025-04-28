package cn.herbal.visualization.Controller;

import cn.herbal.visualization.model.CommentCount;
import cn.herbal.visualization.model.CommentCountByLocation;
import cn.herbal.visualization.model.CommentText;
import cn.herbal.visualization.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductCommentsController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/countDate")
    public List<CommentCount> getCommentCountsByProductNameAndYearMonth(@RequestParam String productName) {
        return commentService.getCommentCountsByProductNameAndYearMonth(productName);
    }

    @GetMapping("/countPlace")
    public List<CommentCountByLocation> getCommentCountsByLocationAndProductName(@RequestParam String productName) {
        return commentService.getCommentCountsByLocationAndProductName(productName);
    }

    @GetMapping("/randomText20")
    public List<CommentText> getRandomCommentTextsByProductName(@RequestParam String productName) {
        return commentService.getRandomCommentTextsByProductName(productName);
    }
}
