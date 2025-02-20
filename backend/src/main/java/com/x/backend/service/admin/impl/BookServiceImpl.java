package com.x.backend.service.admin.impl;

import com.x.backend.mapper.admin.BookMapper;
import com.x.backend.pojo.admin.vo.request.book.SearchVO;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.BookService;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service("adminBookService")
public class BookServiceImpl implements BookService {

    @Resource(name = "adminBookMapper")
    private BookMapper bookMapper;


    @Override
    public List<Book> getAllGameInfo(PageSize pageSize) {
        try {
            pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
            return bookMapper.getAllGameInfo(pageSize);
        } catch (RuntimeException e) {
            log.error("获取游戏信息失败", e);
        }
        return List.of();
    }

    @Override
    public List<Book> getInfoBySearch(SearchVO searchVO) {
        searchVO.setPage((searchVO.getPage() - 1) * searchVO.getSize());
        try {
            return bookMapper.getInfoBySearch(searchVO);
        } catch (RuntimeException e) {
            log.error("搜索游戏信息失败", e);
        }
        return List.of();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteInfo(Long id) {
        try {
            int result = bookMapper.deleteInfo(id);
            if (result != 1) {
                log.error("删除游戏信息失败，id: {}", id);
                throw new RuntimeException("删除游戏信息失败");
            }
        } catch (RuntimeException e) {
            log.error("删除游戏信息失败", e);
            throw new RuntimeException("删除游戏信息失败");
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void createBook(Book book) {
        try {
            int result = bookMapper.insertBook(book);
            if (result != 1) {
                log.error("创建游戏信息失败，book: {}", book);
                throw new RuntimeException("创建游戏信息失败");
            }
        }catch (RuntimeException e) {
            log.error("创建游戏信息失败", e);
            throw new RuntimeException("创建游戏信息失败");
        }
    }

    @Override
    public Book getBookById(Long id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public void updateBook(Book existingBook) {
        bookMapper.update(existingBook);
    }
}

