package com.x.backend.service.user.impl;

import com.x.backend.mapper.user.BookMapper;
import com.x.backend.pojo.common.Book;
import com.x.backend.service.user.BookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return bookMapper.selectTopWelcomeBookInfo(page, size);
    }

    @Override
    public List<Book> selectTopHotBookInfo(int page, int size) {
        return bookMapper.selectTopHotBookInfo(page, size);
    }

}
