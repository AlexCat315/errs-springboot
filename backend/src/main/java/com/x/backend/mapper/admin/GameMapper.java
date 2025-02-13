package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.dto.game.SearchDTO;
import com.x.backend.pojo.admin.vo.request.game.SearchVO;
import com.x.backend.pojo.common.Game;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("adminGameMapper")
public interface GameMapper {

    int insertGame(Game game);

    Game getGameInfoById(Integer gameId);

    List<Game> getGameInfoBySearch(SearchDTO searchDTO);
}
