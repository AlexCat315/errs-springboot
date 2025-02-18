package com.x.backend.controller.user;


import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.request.movie.InsertRatingCommentVO;
import com.x.backend.pojo.user.vo.responses.movie.MovieResponsesVO;
import com.x.backend.pojo.user.vo.request.movie.SearchMovieVO;
import com.x.backend.service.user.MovieService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RoleSecurity(RoleConstants.ROLE_USER)
@RestController("userMoveController")
@RequestMapping("/api/user/movie")
public class MovieController {


    @Resource(name = "userMoveService")
    private MovieService movieService;


    @PostMapping("/get/all")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<MovieResponsesVO>> getAllMoves(@RequestBody PageSize pageSize) {
        return movieService.getAllMoves(pageSize);
    }

    @PostMapping("/get/search")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<MovieResponsesVO>> searchMovies(@RequestBody SearchMovieVO searchMovieVO) {
        return movieService.searchMovies(searchMovieVO);
    }

    @PostMapping("/insert/rating-comment")
    public ResultEntity<String> insertRatingComment(@RequestBody InsertRatingCommentVO insertRatingCommentVO) {
        try {
            return movieService.insertRatingComment(insertRatingCommentVO);
        } catch (RuntimeException e) {
            log.error("error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }
}
