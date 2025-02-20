package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.vo.request.book.SearchVO;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.dto.book.UpdateBookRatingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "adminBookMapper")
@Mapper
public interface BookMapper {
    Integer insertBook(Book book);
    Integer insertScore(ScoreDTO scoreDTO);
    void batchInsertScore(List<ScoreDTO> scoreList);

    Integer updateBookRatingAndUsers(UpdateBookRatingDTO updateBookRatingDTO);

    Integer deleteBookScore(ScoreDTO scoreDTO);

    Integer updateBookUsers(ScoreDTO scoreDTO);

    List<Book> getAllGameInfo(PageSize pageSize);

    List<Book> getInfoBySearch(SearchVO searchVO);

    Integer deleteInfo(Long id);

    Book getBookById(Long id);

    Integer update(Book existingBook);
}
