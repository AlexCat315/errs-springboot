package com.x.backend.service.admin;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.vo.responses.movie.MovieResponsesVO;
import com.x.backend.pojo.common.Movie;
import com.x.backend.pojo.common.PageSize;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface MovieService {
    void createMove(Movie movie);

    ResultEntity<List<MovieResponsesVO>> getAllMovieInfo(PageSize pageSize);

    ResultEntity<MovieResponsesVO> getMovieInfoById(Long id);

    void updateMovieInfo(Movie movie);

    Movie selectById(Integer id);
}
