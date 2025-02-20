package com.x.backend.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.annotation.RoleSecurity;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.vo.request.book.SearchVO;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.BookService;
import com.x.backend.util.MinioUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Slf4j
@RoleSecurity(value = {"admin"})
@RestController("adminBookController")
@RequestMapping("/api/admin/book")
public class BookController {

    @Resource(name = "adminBookService")
    private BookService bookService;

    @Resource
    private MinioUtils minioUtils;

    @Value("${minio.handler-pub-url}")
    private String pubHandlerUrl;


    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/create")
    public ResultEntity<String> createBook(@RequestParam("name") String name,
                                           @RequestParam("author") String author,
                                           @RequestParam("description") String description,
                                           @RequestParam("introduction") String introduction,
                                           @RequestParam("img") MultipartFile img,
                                           @RequestParam("rating") Double rating,
                                           @RequestParam("recommend") Double recommend,
                                           @RequestParam("users") Long users) {
        try {
            // 上传封面图片到 MinIO
            String uploadImgUrlFile = minioUtils.pubUploadFile(img);
            uploadImgUrlFile = pubHandlerUrl + uploadImgUrlFile;


            // 创建书籍记录
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setDescription(description);
            book.setIntroduction(introduction);
            book.setImg(uploadImgUrlFile);
            book.setRating(Float.parseFloat(rating.toString()));
            book.setRecommend(recommend);
            book.setUsers(users);
            book.setCreateTime(new Date()); // 假设 Book 实体有 createTime 字段
            bookService.createBook(book); // 假设 BookService 有 createBook 方法
            return ResultEntity.success("create book success");
        } catch (Exception e) {
            log.error("create book error: {}", e.getMessage(), e);  // 详细记录异常堆栈
            return ResultEntity.failure(e.getMessage());
        }
    }


    @RequestMapping("/test")
    public ResultEntity<String> test() {
        return ResultEntity.success("test");
    }

    @PostMapping("/get/info/all")
    public ResultEntity<List<Book>> getAllGameInfo(@RequestBody PageSize pageSize) {
        try {
            return ResultEntity.success(bookService.getAllGameInfo(pageSize));
        } catch (RuntimeException e) {
            log.error("获取游戏信息失败", e);
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/get/info/search")
    public ResultEntity<List<Book>> getInfoBySearch(@RequestBody SearchVO searchVO) {
        try {
            return ResultEntity.success(bookService.getInfoBySearch(searchVO));
        } catch (RuntimeException e) {
            log.error("搜索游戏信息失败", e);
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/delete/info")
    public ResultEntity<String> deleteInfo(@RequestParam Long id) {
        try {
            bookService.deleteInfo(id);
            return ResultEntity.success("删除成功");
        } catch (RuntimeException e) {
            log.error("删除游戏信息失败", e);
            return ResultEntity.failure(e.getMessage());
        }
    }

}