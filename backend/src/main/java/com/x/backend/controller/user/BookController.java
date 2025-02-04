package com.x.backend.controller.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Book;
import com.x.backend.service.user.BookService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("userBookController")
@RequestMapping("/api/user/book")
public class BookController {

    @Resource(name = "userBookService")
    private BookService bookService;

    @GetMapping("/top-250")
    public ResultEntity<List<Book>> selectTop250BookInfo(@RequestParam(defaultValue = "0") int start) {
        try {
            // 默认查询 25 条数据
            int page = start / 25;  // 计算当前页数
            int size = 25;  // 每页 25 条数据

            // 调用服务层进行分页查询
            return ResultEntity.success(bookService.selectTop250BookInfo(page, size));
        } catch (RuntimeException e) {
            log.error("top-250发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

}
