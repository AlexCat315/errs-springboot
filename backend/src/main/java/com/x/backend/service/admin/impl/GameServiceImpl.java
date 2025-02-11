package com.x.backend.service.admin.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.admin.GameMapper;
import com.x.backend.pojo.admin.entity.Game;
import com.x.backend.pojo.admin.vo.request.game.GameCreateRequest;
import com.x.backend.service.admin.GameService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("adminGameService")
public class GameServiceImpl implements GameService {

    @Resource(name = "adminGameMapper")
    private GameMapper gameMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createGame(GameCreateRequest request) {
        // 创建一个新的 Game 实体对象
        Game game = new Game();

        // 设置 Game 对象的属性
        game.setGameName(request.getGameName());
        game.setGameScore(request.getGameScore());
        game.setGameDeveloper(request.getGameDeveloper());
        game.setReleaseDate(request.getReleaseDate());
        game.setGameDescription(request.getGameDescription());

        // 使用 Jackson 将 List 转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将 List 转换为 JSON 字符串
            game.setGameCategories(objectMapper.writeValueAsString(request.getGameCategories()));
            game.setGamePlatforms(objectMapper.writeValueAsString(request.getGamePlatforms()));
        } catch (JsonProcessingException e) {
            log.error("转换 GameCategories 或 GamePlatforms 时发生错误:{}", e.getMessage(), e);
        }

        game.setGameImageUrl(request.getGameImageUrl());

        // 调用 Mapper 的插入方法
        int insertGame = gameMapper.insertGame(game);
        if (insertGame != 1) {
            throw new RuntimeException("插入 Game 数据失败");
        }
    }

}
