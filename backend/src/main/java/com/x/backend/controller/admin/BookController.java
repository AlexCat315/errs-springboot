package com.x.backend.controller.admin;

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


    @Transactional(rollbackFor = RuntimeException.class)
    @PutMapping("/update/{id}")  // 使用 PUT 请求，并在路径中包含书籍 ID
    public ResultEntity<String> updateBook(@PathVariable("id") Long id,  // 从路径中获取书籍 ID
                                           @RequestParam("name") String name,
                                           @RequestParam("author") String author,
                                           @RequestParam("description") String description,
                                           @RequestParam("introduction") String introduction,
                                           @RequestParam(value = "img", required = false) MultipartFile img, // 图片是可选的
                                           @RequestParam("rating") Double rating,
                                           @RequestParam("recommend") Double recommend,
                                           @RequestParam("users") Long users) {
        try {
            // 1. 检查书籍是否存在
            Book existingBook = bookService.getBookById(id);  // 假设 bookService 有 getBookById 方法
            if (existingBook == null) {
                return ResultEntity.failure("Book with id " + id + " not found.");
            }

            // 2. 更新书籍信息
            existingBook.setName(name);
            existingBook.setAuthor(author);
            existingBook.setDescription(description);
            existingBook.setIntroduction(introduction);
            existingBook.setRating(Float.parseFloat(rating.toString()));
            existingBook.setRecommend(recommend);
            existingBook.setUsers(users);


            // 3. 处理图片更新 (如果提供了新的图片)
            if (img != null && !img.isEmpty()) {
                String uploadImgUrlFile = minioUtils.pubUploadFile(img);
                uploadImgUrlFile = pubHandlerUrl + uploadImgUrlFile;
                existingBook.setImg(uploadImgUrlFile);

                 String oldImageUrl = existingBook.getImg();
                 if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    minioUtils.pubDeleteFile(oldImageUrl.replace(pubHandlerUrl, "")); // 从 URL 中提取文件名
                 }
            }

            // 4. 保存更新后的书籍
            bookService.updateBook(existingBook);  // 假设 bookService 有 updateBook 方法
            return ResultEntity.success("Book updated successfully.");

        } catch (Exception e) {
            log.error("Update book error: {}", e.getMessage(), e);
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