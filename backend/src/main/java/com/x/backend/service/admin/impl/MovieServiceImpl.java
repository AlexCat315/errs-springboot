package com.x.backend.service.admin.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.admin.MovieMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Movie;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.admin.vo.responses.movie.MovieResponsesVO;
import com.x.backend.service.admin.MovieService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("adminMoveService")
public class MovieServiceImpl implements MovieService {

    @Resource(name = "adminMoveMapper")
    private MovieMapper movieMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void createMove(Movie movie) {
        try {
            Integer mapperMove = movieMapper.createMove(movie);
            if (mapperMove != 1) {
                throw new RuntimeException("create movie failed");
            }
        } catch (RuntimeException e) {
            log.error("create movie failed, cause: {}", e.getMessage());
            throw e;
        }

    }

    @Override
    public ResultEntity<List<MovieResponsesVO>> getAllMovieInfo(PageSize pageSize) {
        try {
            pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
            List<Movie> movies = movieMapper.getAllMovieInfo(pageSize);
            List<MovieResponsesVO> movieResponsesVOS = new ArrayList<>();
            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();
            // 循环遍历 movies，将 Movie 对象转换为 MovieResponsesVO 对象
            for (Movie movie : movies) {
                List<String> types = null;
                MovieResponsesVO movieResponsesVO = new MovieResponsesVO();
                BeanUtils.copyProperties(movie, movieResponsesVO);
                try {
                    types = objectMapper.readValue(movie.getTypes(), new TypeReference<List<String>>() {
                    });
                } catch (JsonProcessingException e) {
                    log.error("Error parsing gameCategories JSON", e);
                    throw new RuntimeException("Error parsing gameCategories JSON", e);
                }
                movieResponsesVO.setTypes(types);
                movieResponsesVOS.add(movieResponsesVO);
            }
            return ResultEntity.success(movieResponsesVOS);

        } catch (Exception e) {
            log.error("Error getting all moves", e);
            return ResultEntity.failure("Error getting all moves");
        }
    }


    @Override
    public ResultEntity<MovieResponsesVO> getMovieInfoById(Long id) {
        try {
            Movie movie = movieMapper.getMovieInfoById(id);
            if (movie == null) {
                return ResultEntity.failure("Movie not found");
            }
            MovieResponsesVO movieResponsesVO = new MovieResponsesVO();
            BeanUtils.copyProperties(movie, movieResponsesVO);
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> types = null;
            try {
                types = objectMapper.readValue(movie.getTypes(), new TypeReference<List<String>>() {
                });
            } catch (JsonProcessingException e) {
                log.error("Error parsing gameCategories JSON", e);
                throw new RuntimeException("Error parsing gameCategories JSON", e);
            }
            movieResponsesVO.setTypes(types);
            return ResultEntity.success(movieResponsesVO);
        } catch (Exception e) {
            log.error("Error getting movie by id", e);
            return ResultEntity.failure("Error getting movie by id");
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateMovieInfo(Movie movie) {
        try {
            Integer mapperMove = movieMapper.updateMovie(movie);
            if (mapperMove != 1) {
                throw new RuntimeException("update movie failed");
            }
        } catch (RuntimeException e) {
            log.error("update movie failed, cause: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Movie selectById(Integer id) {
        return movieMapper.selectById(id);
    }

    @Override
    public void deleteMovieInfo(Integer id) {
        Integer i = movieMapper.deleteMovieInfo(id);
        if (i != 1) {
            throw new RuntimeException("delete movie failed");
        }
    }
}
