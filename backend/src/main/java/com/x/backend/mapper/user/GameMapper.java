package com.x.backend.mapper.user;


import com.x.backend.pojo.common.Game;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userGameMapper")
public interface GameMapper {

    List<Game> getTop50(@Param("offset") int offset, @Param("size")int size);

}
