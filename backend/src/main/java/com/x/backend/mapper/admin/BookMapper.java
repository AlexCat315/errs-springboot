package com.x.backend.mapper.admin;

import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.dto.book.UpdateBookRatingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "adminBookMapper")
@Mapper
public interface BookMapper {
    Integer insertBook(Book book);
    Integer score(ScoreDTO scoreDTO);
    void batchUpdateScore(List<ScoreDTO> scoreList);

    Integer updateBookRatingAndUsers(UpdateBookRatingDTO updateBookRatingDTO);
}
