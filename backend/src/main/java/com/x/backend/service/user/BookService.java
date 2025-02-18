package com.x.backend.service.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.IsLikeBook;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.vo.request.book.CommentVO;
import com.x.backend.pojo.user.vo.request.book.ScoreVo;
import com.x.backend.pojo.user.vo.request.book.SearchBookVO;
import jakarta.validation.Valid;

import java.util.List;

public interface BookService {
    List<Book> selectTop250BookInfo(int page, int size);

    List<Book> selectTop50BookInfo(int page, int size);

    List<Book> selectTopWelcomeBookInfo(int page, int size);

    List<Book> selectTopHotBookInfo(int page, int size);

    Book selectBookDetail(Long id);

    Boolean  validateLike(IsLikeBook isLike);

    void insertScore(ScoreVo scoreVo);

    ResultEntity<Boolean> validateScore(ScoreDTO scoreDTO);

    void updateScore( ScoreVo scoreVo);

    void insertComment(@Valid CommentVO commentVO);

    List<Book> searchBook(SearchBookVO searchBookVO);
}
