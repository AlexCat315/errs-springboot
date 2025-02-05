package com.x.backend.service.user;

import com.x.backend.pojo.common.Book;

import java.util.List;

public interface BookService {
    List<Book> selectTop250BookInfo(int page, int size);

    List<Book> selectTop50BookInfo(int page, int size);

    List<Book> selectTopWelcomeBookInfo(int page, int size);

    List<Book> selectTopHotBookInfo(int page, int size);
}
