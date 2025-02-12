package com.x.backend.service.user;


import com.x.backend.pojo.user.vo.request.game.GameRantingCommentVO;
import com.x.backend.pojo.user.vo.responses.game.GameResponsesVO;

import java.util.List;

public interface GameService {

    List<GameResponsesVO> getTop50(int start, int size);

    Boolean addRantingComment(GameRantingCommentVO gameRantingCommentVO);

    List<GameResponsesVO> getHighestRated(int start, int size);

    List<GameResponsesVO> getMostReviewed(int start, int size);
}
