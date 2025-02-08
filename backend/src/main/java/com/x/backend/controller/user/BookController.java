package com.x.backend.controller.user;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.IsLikeBook;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.book.ScoreVo;
import com.x.backend.service.user.BookService;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

@Slf4j
@RestController("userBookController")
@RequestMapping("/api/user/book")
public class BookController {

    @Resource(name = "userBookService")
    private BookService bookService;
    @Resource
    private JWTUtils<UserAccount> jwtUtils;

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

    @GetMapping("/detail")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<Book> selectBookDetail(@RequestParam Long id) {
        try {
            return ResultEntity.success(bookService.selectBookDetail(id));
        } catch (RuntimeException e) {
            log.error("book/detail发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/validate/like")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<Boolean> validateLike(@RequestParam Long bId) {
        try {
            Integer uId = jwtUtils.getId();
            IsLikeBook isLike = new IsLikeBook(bId, uId);
            return ResultEntity.success(bookService.validateLike(isLike));
        } catch (RuntimeException e) {
            log.error("book/validate-like发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/score")
    @RoleSecurity(RoleConstants.ROLE_USER)
    public ResultEntity<String> score(@Valid @RequestBody ScoreVo scoreVo) {
        try {
            if (scoreVo.getScore() < 0 || scoreVo.getScore() > 10) {
                return ResultEntity.failure("评分范围为 0-10");
            }
            bookService.score(scoreVo);
            return ResultEntity.success();
        } catch (RuntimeException e) {
            log.error("book/score发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/validate/score")
    @RoleSecurity(RoleConstants.ROLE_USER)
    public ResultEntity<Boolean> validateScore(@RequestParam Long bId)  {
        try {
            Integer uId = jwtUtils.getId();
            ScoreDTO  scoreDTO = new ScoreDTO();
            scoreDTO.setBId(bId);
            scoreDTO.setAId(uId);
            return bookService.validateScore(scoreDTO);
        } catch (RuntimeException e) {
            log.error("book/validate-score发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }






}
