package com.x.backend.mapper.user;

import com.x.backend.pojo.common.Movie;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.dto.movie.InsertRatingCommentDTO;
import com.x.backend.pojo.user.vo.responses.movie.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userMoveMapper")
public interface MovieMapper {

    List<Movie> selectAllMoves(PageSize pageSize);

    int insertRatingComment(InsertRatingCommentDTO insertRatingCommentDTO);

    int updateRating(@Param("movieId") Long movieId, @Param("rating") Double rating);

    List<Movie> searchMovies(SearchVO searchVO);
}
