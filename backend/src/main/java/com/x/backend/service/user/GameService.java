package com.x.backend.service.user;


import com.x.backend.pojo.user.vo.responses.game.GameResponsesVO;

import java.util.List;

public interface GameService {

    List<GameResponsesVO> getTop50(int start, int size);
}
