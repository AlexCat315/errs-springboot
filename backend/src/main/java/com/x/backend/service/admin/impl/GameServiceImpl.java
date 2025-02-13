package com.x.backend.service.admin.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.admin.GameMapper;
import com.x.backend.pojo.admin.dto.game.SearchDTO;
import com.x.backend.pojo.admin.vo.request.game.SearchVO;
import com.x.backend.pojo.admin.vo.responses.game.GameResponsesVO;
import com.x.backend.pojo.common.Game;
import com.x.backend.pojo.admin.vo.request.game.GameCreateRequest;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.GameService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Override
    public GameResponsesVO getGameInfoById(Integer gameId) {
        try {
            // 查询数据库获取游戏信息
            Game game = gameMapper.getGameInfoById(gameId);
            if (game == null) {
                throw new RuntimeException("Game not found with ID: " + gameId);
            }

            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 解析 gameCategories 和 gamePlatforms
            List<String> gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<>() {
            });
            List<String> gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<>() {
            });

            // 创建 GameResponsesVO 对象并复制属性
            GameResponsesVO gameResponsesVO = new GameResponsesVO();
            BeanUtils.copyProperties(game, gameResponsesVO);
            gameResponsesVO.setGameCategories(gameCategories);
            gameResponsesVO.setGamePlatforms(gamePlatforms);

            return gameResponsesVO;
        } catch (Exception e) {
            log.error("Error getting game info by ID: {}", gameId, e);
            throw new RuntimeException("Error getting game info by ID: " + gameId, e);
        }
    }

    @Override
    public List<GameResponsesVO> getGameInfoBySearch(SearchVO searchVO) {
        try {
            // 查询数据库获取游戏信息
            SearchDTO searchDTO = new SearchDTO();
            BeanUtils.copyProperties(searchVO, searchDTO);
            searchDTO.setStart((searchVO.getPage() - 1) * searchVO.getSize());
            List<Game> games = gameMapper.getGameInfoBySearch(searchDTO);

            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 解析 gameCategories 和 gamePlatforms
            // 创建 GameResponsesVO 对象并复制属性
            return games.stream().map(game -> {
                try {
                    List<String> gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<>() {
                    });
                    List<String> gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<>() {
                    });

                    // 创建 GameResponsesVO 对象并复制属性
                    GameResponsesVO gameResponsesVO = new GameResponsesVO();
                    BeanUtils.copyProperties(game, gameResponsesVO);
                    gameResponsesVO.setGameCategories(gameCategories);
                    gameResponsesVO.setGamePlatforms(gamePlatforms);

                    return gameResponsesVO;
                } catch (Exception e) {
                    log.error("Error getting game info by search: {}", searchVO, e);
                    throw new RuntimeException("Error getting game info by search: " + searchVO, e);
                }
            }).toList();
        } catch (Exception e) {
            log.error("Error getting game info by search: {}", searchVO, e);
            throw new RuntimeException("Error getting game info by search: " + searchVO, e);
        }
    }

    @Override
    public List<GameResponsesVO> getAllGameInfo(PageSize pageSize) {
        // 查询数据库获取游戏信息
        pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
        List<Game> games = gameMapper.getAllGameInfo(pageSize);
        // 创建 ObjectMapper 实例
        ObjectMapper objectMapper = new ObjectMapper();

        // 解析 gameCategories 和 gamePlatforms
        // 创建 GameResponsesVO 对象并复制属性
        return games.stream().map(game -> {
            try {
                List<String> gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<>() {
                });
                List<String> gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<>() {
                });

                // 创建 GameResponsesVO 对象并复制属性
                GameResponsesVO gameResponsesVO = new GameResponsesVO();
                BeanUtils.copyProperties(game, gameResponsesVO);
                gameResponsesVO.setGameCategories(gameCategories);
                gameResponsesVO.setGamePlatforms(gamePlatforms);

                return gameResponsesVO;
            } catch (Exception e) {
                log.error("Error getting all game info: {}", pageSize, e);
                throw new RuntimeException("Error getting all game info: " + pageSize, e);
            }
        }).toList();
    }

    @Override
    public String getGameImageUrl(Integer gameId) {
        // 查询数据库获取游戏图片 URL
        String gameImageUrl = gameMapper.getGameImageUrl(gameId);
        if (gameImageUrl != null) {
            return gameImageUrl;
        } else {
            throw new RuntimeException("Game image not found with ID: " + gameId);
        }
    }

    @Override
    public void updateGame(GameCreateRequest request) {
        // 创建一个新的 Game 实体对象
        Game game = new Game();

        // 设置 Game 对象的属性
        game.setId(request.getGameId());
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
        // 调用 Mapper 的更新方法
        int updateGame = gameMapper.updateGame(game);
        if (updateGame != 1) {
            throw new RuntimeException("更新 Game 数据失败");
        }
    }
}
