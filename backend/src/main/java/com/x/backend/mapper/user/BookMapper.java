package com.x.backend.mapper.user;

import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.CommentDTO;
import com.x.backend.pojo.user.dto.book.IsLikeBook;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.dto.book.UpdateBookRatingDTO;
import com.x.backend.pojo.user.vo.request.book.ScoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "userBookMapper")
@Mapper
public interface BookMapper {
    List<Book> selectTop250BookInfo(@Param("offset") int offset, @Param("size") int size);

    List<Book> selectTop50BookInfo(@Param("offset") int offset, @Param("size") int size);

    List<Book> selectTopWelcomeBookInfo(@Param("offset") int offset, @Param("size") int size);

    List<Book> selectTopHotBookInfo(@Param("offset") int offset, @Param("size") int size);

    Book selectBookDetail(Long id);

    @Select("SELECT EXISTS (SELECT 1 FROM book_like WHERE a_id = #{aId} AND b_id = #{bId}) AS is_like")
    Boolean validateLike(IsLikeBook isLike);

    Boolean validateScore(ScoreDTO scoreDTO);

    int insertComment(CommentDTO commentDTO);
}
