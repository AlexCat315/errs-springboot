package com.x.backend.service.admin;

import com.x.backend.pojo.admin.vo.request.book.SearchVO;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.common.PageSize;

import java.util.List;

public interface BookService {
    List<Book> getAllGameInfo(PageSize pageSize);

    List<Book> getInfoBySearch(SearchVO searchVO);

    void deleteInfo(Long id);

    void createBook(Book book);
}
