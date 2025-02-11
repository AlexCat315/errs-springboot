package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.entity.Game;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("adminGameMapper")
public interface GameMapper {

    int insertGame(Game game);
}
