package com.x.backend.service.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.user.GameMapper;
import com.x.backend.pojo.common.Game;
import com.x.backend.pojo.user.vo.responses.game.GameResponsesVO;
import com.x.backend.service.user.GameService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userGameService")
public class GameServiceImpl implements GameService {

    @Resource(name = "userGameMapper")
    private GameMapper gameMapper;


    @Override
    public List<GameResponsesVO> getTop50(int start, int size) {
        try {
            // 获取 Game 列表
            List<Game> games = gameMapper.getTop50(start, size);

            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 存储转换后的结果
            List<GameResponsesVO> gameResponsesVOList = new ArrayList<>();

            // 遍历 Game 列表并转换
            for (Game game : games) {
                // 转换 gameCategories
                List<String> gameCategories = null;
                try {
                    gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<List<String>>() {});
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error parsing gameCategories JSON", e);
                }

                // 转换 gamePlatforms
                List<String> gamePlatforms = null;
                try {
                    gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<List<String>>() {});
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error parsing gamePlatforms JSON", e);
                }

                // 创建 GameResponsesVO 对象并复制属性
                GameResponsesVO gameResponsesVO = new GameResponsesVO();
                BeanUtils.copyProperties(game, gameResponsesVO);

                // 设置转换后的列表
                gameResponsesVO.setGameCategories(gameCategories);
                gameResponsesVO.setGamePlatforms(gamePlatforms);

                // 将 gameResponsesVO 添加到列表
                gameResponsesVOList.add(gameResponsesVO);
            }

            return gameResponsesVOList;
        } catch (Exception e) {
            log.error("Error getting top 50 games", e);
            throw new RuntimeException("Error getting top 50 games", e);
        }
    }



}
