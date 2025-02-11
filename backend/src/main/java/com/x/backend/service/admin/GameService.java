package com.x.backend.service.admin;

import com.x.backend.pojo.admin.vo.request.game.GameCreateRequest;

public interface GameService {
    void createGame(GameCreateRequest request);
}
