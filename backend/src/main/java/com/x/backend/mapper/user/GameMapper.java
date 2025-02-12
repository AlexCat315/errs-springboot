package com.x.backend.mapper.user;


import com.x.backend.pojo.common.Game;
import com.x.backend.pojo.user.dto.game.GameRantingCommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userGameMapper")
public interface GameMapper {

    List<Game> getTop50(@Param("offset") int offset, @Param("size")int size);

    Integer updateRanting(GameRantingCommentDTO gameRantingCommentDTO);

    Integer insertRantingComment(GameRantingCommentDTO gameRantingCommentDTO);

    List<Game> getHighestRated(int start, int size);

    List<Game> getMostReviewed(int start, int size);
}
