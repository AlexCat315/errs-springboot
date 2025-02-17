package com.x.backend.service.admin.impl;

import com.x.backend.mapper.admin.MovieMapper;
import com.x.backend.pojo.common.Movie;
import com.x.backend.service.admin.MovieService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        }catch (RuntimeException e){
            log.error("create movie failed, cause: {}", e.getMessage());
            throw e;
        }

    }
}
