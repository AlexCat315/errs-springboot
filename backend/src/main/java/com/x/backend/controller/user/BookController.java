package com.x.backend.controller.user;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
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
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<Book>> selectTop250BookInfo(@RequestParam(defaultValue = "0") int start) {
        try {
            int size = 25;  // 每页 25 条数据
            return ResultEntity.success(bookService.selectTop250BookInfo(start, size));
        } catch (RuntimeException e) {
            log.error("top-250发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/top-50")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<Book>> selectTop50BookInfo(@RequestParam(defaultValue = "0") int start) {
        try {
            int size = 10;  // 每页 10 条数据
            return ResultEntity.success(bookService.selectTop50BookInfo(start, size));
        } catch (RuntimeException e) {
            log.error("top-50发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/top-welcome")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<Book>> selectTopWelcomeBookInfo(@RequestParam(defaultValue = "0") int start) {
        try {
            int size = 10;  // 每页 10 条数据
            return ResultEntity.success(bookService.selectTopWelcomeBookInfo(start, size));
        } catch (RuntimeException e) {
            log.error("top-welcome发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/top-hot")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<Book>> selectTopHotBookInfo(@RequestParam(defaultValue = "0") int start) {
        try {
            int size = 10;  // 每页 10 条数据
            return ResultEntity.success(bookService.selectTopHotBookInfo(start, size));
        } catch (RuntimeException e) {
            log.error("top-hot发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

}
