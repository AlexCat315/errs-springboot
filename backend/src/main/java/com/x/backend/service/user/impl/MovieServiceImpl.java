package com.x.backend.service.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.user.MovieMapper;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Movie;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.responses.movie.MovieResponsesVO;
import com.x.backend.service.user.MovieService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userMoveService")
public class MovieServiceImpl implements MovieService {

    @Resource(name = "userMoveMapper")
    private MovieMapper movieMapper;

    @Override
    public ResultEntity<List<MovieResponsesVO>> getAllMoves(PageSize pageSize) {
        try {
            pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
            List<Movie> movies = movieMapper.selectAllMoves(pageSize);
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


}
