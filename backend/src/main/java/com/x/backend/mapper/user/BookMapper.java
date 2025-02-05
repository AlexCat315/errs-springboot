package com.x.backend.mapper.user;

import com.x.backend.pojo.common.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "userBookMapper")
@Mapper
public interface BookMapper {
    List<Book> selectTop250BookInfo(@Param("offset") int offset, @Param("size") int size);

    List<Book> selectTop50BookInfo(@Param("offset") int offset, @Param("size") int size);

    List<Book> selectTopWelcomeBookInfo(@Param("offset") int offset, @Param("size") int size);

    List<Book> selectTopHotBookInfo(@Param("offset") int offset, @Param("size") int size);
}
