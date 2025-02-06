package com.x.backend.mapper.user;

import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.IsLikeBook;
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

    @Select("SELECT EXISTS (SELECT 1 FROM like_book WHERE a_id = #{aId} AND b_id = #{bId}) AS is_like")
    Boolean validateLike(IsLikeBook isLike);
}
