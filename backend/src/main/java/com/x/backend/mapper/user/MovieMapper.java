package com.x.backend.mapper.user;

import com.x.backend.pojo.common.Movie;
import com.x.backend.pojo.common.PageSize;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userMoveMapper")
public interface MovieMapper {

    List<Movie> selectAllMoves(PageSize pageSize);
}
