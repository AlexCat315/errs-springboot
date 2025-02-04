package com.x.backend.mapper.admin;

import com.x.backend.pojo.common.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component(value = "adminBookMapper")
@Mapper
public interface BookMapper {
    Integer insertBook(Book book);
}
