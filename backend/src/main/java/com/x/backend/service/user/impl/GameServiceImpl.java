package com.x.backend.service.user.impl;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.user.GameMapper;
import com.x.backend.pojo.common.Game;
import com.x.backend.pojo.user.dto.game.FavoritesGameDTO;
import com.x.backend.pojo.user.dto.game.GameRantingCommentDTO;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.game.GameRantingCommentVO;
import com.x.backend.pojo.user.vo.request.game.SearchGameVO;
import com.x.backend.pojo.user.vo.responses.game.GameResponsesVO;
import com.x.backend.service.user.GameService;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userGameService")
public class GameServiceImpl implements GameService {

    @Resource(name = "userGameMapper")
    private GameMapper gameMapper;
    @Resource
    private JWTUtils<UserAccount> accountUtils;


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
                    gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<List<String>>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error parsing gameCategories JSON", e);
                }

                // 转换 gamePlatforms
                List<String> gamePlatforms = null;
                try {
                    gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<List<String>>() {
                    });
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

    @Override
    public List<GameResponsesVO> getHighestRated(int start, int size) {
        try {
            // 获取 Game 列表
            List<Game> games = gameMapper.getHighestRated(start, size);

            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 存储转换后的结果
            List<GameResponsesVO> gameResponsesVOList = new ArrayList<>();

            // 遍历 Game 列表并转换
            for (Game game : games) {
                // 转换 gameCategories
                List<String> gameCategories = null;
                try {
                    gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<List<String>>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error parsing gameCategories JSON", e);
                }

                // 转换 gamePlatforms
                List<String> gamePlatforms = null;
                try {
                    gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<List<String>>() {
                    });
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
            log.error("Error getting getHighestRated games", e);
            throw new RuntimeException("Error getting getHighestRated games", e);
        }
    }

    @Override
    public List<GameResponsesVO> getMostReviewed(int start, int size) {
        try {
            // 获取 Game 列表
            List<Game> games = gameMapper.getMostReviewed(start, size);

            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 存储转换后的结果
            List<GameResponsesVO> gameResponsesVOList = new ArrayList<>();

            // 遍历 Game 列表并转换
            for (Game game : games) {
                // 转换 gameCategories
                List<String> gameCategories = null;
                try {
                    gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<List<String>>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error parsing gameCategories JSON", e);
                }

                // 转换 gamePlatforms
                List<String> gamePlatforms = null;
                try {
                    gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<List<String>>() {
                    });
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
            log.error("Error getting getHighestRated games", e);
            throw new RuntimeException("Error getting getHighestRated games", e);
        }
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean addRantingComment(GameRantingCommentVO gameRantingCommentVO) {
        Integer id = accountUtils.getId();
        GameRantingCommentDTO gameRantingCommentDTO = new GameRantingCommentDTO();
        BeanUtils.copyProperties(gameRantingCommentVO, gameRantingCommentDTO);
        gameRantingCommentDTO.setUserID(id);
        gameRantingCommentDTO.setDate(new DateTime(LocalDateTime.now()));
        try {
            Integer updateRanting = gameMapper.updateRanting(gameRantingCommentDTO);
            if (updateRanting == 1) {
                Integer insertRantingComment = gameMapper.insertRantingComment(gameRantingCommentDTO);
                if (insertRantingComment != 1) {
                    throw new RuntimeException("Error adding ranting comment");
                }
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            log.error("Error adding ranting comment", e);
            throw new RuntimeException("Error adding ranting comment", e);
        }
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addFavoritesGame(Integer gameId) {
        try {
            Integer id = accountUtils.getId();
            Integer i = gameMapper.addFavoritesGame(new FavoritesGameDTO(gameId, id));
            if (i != 1) {
                throw new RuntimeException("Error adding favorites game");
            }
        } catch (RuntimeException e) {
            log.error("Error adding favorites game", e);
            throw new RuntimeException("Error adding favorites game", e);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteFavoritesGame(Integer gameId) {
        try {
            Integer id = accountUtils.getId();
            Integer i = gameMapper.deleteFavoritesGame(new FavoritesGameDTO(gameId, id));
            if (i != 1) {
                throw new RuntimeException("Error removing favorites game");
            }
        } catch (RuntimeException e) {
            log.error("Error removing favorites game", e);
            throw new RuntimeException("Error removing favorites game", e);
        }
    }

    @Override
    public Boolean getStateFavoritesGame(Integer gameId) {
        try {
            Integer id = accountUtils.getId();
            return gameMapper.getStateFavoritesGame(new FavoritesGameDTO(gameId, id)) == 1;
        } catch (Exception e) {
            log.error("Error getting favorites game state", e);
            throw new RuntimeException("Error getting favorites game state", e);
        }
    }

    @Override
    public List<GameResponsesVO> searchGame(SearchGameVO searchGameVO) {
        try {
            // 获取 Game 列表
            searchGameVO.setStart((searchGameVO.getPage() - 1) * searchGameVO.getSize());
            List<Game> games = gameMapper.searchGame(searchGameVO);

            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 存储转换后的结果
            List<GameResponsesVO> gameResponsesVOList = new ArrayList<>();

            // 遍历 Game 列表并转换
            for (Game game : games) {
                // 转换 gameCategories
                List<String> gameCategories = null;
                try {
                    gameCategories = objectMapper.readValue(game.getGameCategories(), new TypeReference<List<String>>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error parsing gameCategories JSON", e);
                }

                // 转换 gamePlatforms
                List<String> gamePlatforms = null;
                try {
                    gamePlatforms = objectMapper.readValue(game.getGamePlatforms(), new TypeReference<List<String>>() {
                    });
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
            log.error("Error searching game", e);
            throw new RuntimeException("Error searching game", e);
        }
    }


}
