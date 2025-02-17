package com.x.backend.service.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.request.movie.InsertRatingCommentVO;
import com.x.backend.pojo.user.vo.responses.movie.MovieResponsesVO;

import java.util.List;

public interface MovieService {

    ResultEntity<List<MovieResponsesVO>> getAllMoves(PageSize pageSize);

    ResultEntity<String> insertRatingComment(InsertRatingCommentVO insertRatingCommentVO);
}
