package com.x.backend.service.user.impl;

import com.x.backend.mapper.user.BookMapper;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.IsLikeBook;
import com.x.backend.service.user.BookService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Component("userBookService")
@Service
public class BookServiceImpl implements BookService {

    @Resource(name = "userBookMapper")
    private BookMapper bookMapper;

    @Override
    public List<Book> selectTop250BookInfo(int page, int size) {
        return bookMapper.selectTop250BookInfo(page, size);
    }

    @Override
    public List<Book> selectTop50BookInfo(int page, int size) {
        return bookMapper.selectTop50BookInfo(page, size);
    }

    @Override
    public List<Book> selectTopWelcomeBookInfo(int page, int size) {
        try {
            return bookMapper.selectTopWelcomeBookInfo(page, size);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> selectTopHotBookInfo(int page, int size) {
        try {
            return bookMapper.selectTopHotBookInfo(page, size);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book selectBookDetail(Long id) {
        try {
            return bookMapper.selectBookDetail(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean validateLike(IsLikeBook isLike) {
        try {
            return bookMapper.validateLike(isLike);
        } catch (RuntimeException e) {
            log.error("validateLike error", e);
            throw new RuntimeException(e);
        }
    }
}
