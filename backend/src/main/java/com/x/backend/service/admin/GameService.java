package com.x.backend.service.admin;

import com.x.backend.pojo.admin.vo.request.game.GameCreateRequest;
import com.x.backend.pojo.admin.vo.request.game.SearchVO;
import com.x.backend.pojo.admin.vo.responses.game.GameResponsesVO;
import com.x.backend.pojo.common.PageSize;

import java.util.List;

public interface GameService {
    void createGame(GameCreateRequest request);

    GameResponsesVO getGameInfoById(Integer gameId);

    List<GameResponsesVO> getGameInfoBySearch(SearchVO searchVO);

    List<GameResponsesVO> getAllGameInfo(PageSize pageSize);
}
