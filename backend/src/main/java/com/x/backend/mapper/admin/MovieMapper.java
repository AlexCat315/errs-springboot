package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.vo.request.movie.SearchMovieVO;
import com.x.backend.pojo.common.Movie;
import com.x.backend.pojo.common.PageSize;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("adminMoveMapper")
public interface MovieMapper {
    Integer createMove(Movie movie);

    List<Movie> getAllMovieInfo(PageSize pageSize);

    Movie getMovieInfoById(Long id);


    Integer updateMovie(Movie movie);

    Movie selectById(Integer id);

    Integer deleteMovieInfo(Integer id);

    List<Movie> getMovieInfoSearch(SearchMovieVO searchMovieVO);
}
