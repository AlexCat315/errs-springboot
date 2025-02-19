package com.x.backend.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.annotation.RoleSecurity;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.vo.request.game.GameCreateRequest;
import com.x.backend.pojo.admin.vo.request.game.SearchVO;
import com.x.backend.pojo.admin.vo.responses.game.GameResponsesVO;
import com.x.backend.pojo.common.Game;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.GameService;
import com.x.backend.util.MinioUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RoleSecurity(value = {"admin"})
@RestController("adminGameController")
@RequestMapping("/api/admin/game")
public class GameController {

    @Resource
    private MinioUtils minioUtils;
    @Resource(name = "adminGameService")
    private GameService gameService;
    @Value("${minio.handler-pub-url}")
    private String pubHandlerUrl;

    @PostMapping(value = "/insert/score", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity<String> createGame(
            @RequestParam("gameName") String gameName,
            @RequestParam(value = "gameScore", required = false) Double gameScore,
            @RequestParam("gameDeveloper") String gameDeveloper,
            @RequestParam("releaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,
            @RequestParam("gameDescription") String gameDescription,
            @RequestParam("gameCategories") String gameCategories,
            @RequestParam("gamePlatforms") String gamePlatforms,
            @RequestParam(value = "file", required = false) MultipartFile file) throws JsonProcessingException {

        // 处理 JSON 数组
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> categories = objectMapper.readValue(gameCategories, new TypeReference<>() {
        });
        List<String> platforms = objectMapper.readValue(gamePlatforms, new TypeReference<>() {
        });

        // 构建请求对象
        GameCreateRequest request = new GameCreateRequest();
        request.setGameName(gameName);
        request.setGameScore(gameScore);
        request.setGameDeveloper(gameDeveloper);
        request.setReleaseDate(releaseDate);
        request.setGameDescription(gameDescription);
        request.setGameCategories(categories);
        request.setGamePlatforms(platforms);

        // 处理文件上传
        if (file != null && !file.isEmpty()) {
            try {
                String uploadFile = minioUtils.pubUploadFile(file);
                uploadFile = pubHandlerUrl + uploadFile;
                request.setGameImageUrl(uploadFile);
                gameService.createGame(request);
                return ResultEntity.success();
            } catch (Exception e) {
                log.error("上传文件失败", e);
                return ResultEntity.serverError();
            }
        }
        return ResultEntity.failure("上传文件失败");
    }


    @PostMapping("/get/info")
    public ResultEntity<GameResponsesVO> getGameInfoById(@RequestParam("gameId") Integer gameId) {
        try {
            return ResultEntity.success(gameService.getGameInfoById(gameId));
        } catch (RuntimeException e) {
            log.error("获取游戏信息失败", e);
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/get/info/search")
    public ResultEntity<List<GameResponsesVO>> getGameInfoBySearch(@RequestBody SearchVO searchVO) {
        try {
            return ResultEntity.success(gameService.getGameInfoBySearch(searchVO));
        } catch (RuntimeException e) {
            log.error("获取游戏信息失败", e);
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/get/info/all")
    public ResultEntity<List<GameResponsesVO>> getAllGameInfo(@RequestBody PageSize pageSize) {
        try {
            return ResultEntity.success(gameService.getAllGameInfo(pageSize));
        } catch (RuntimeException e) {
            log.error("获取游戏信息失败", e);
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping(value = "/update/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity<String> updateGame(
            @RequestParam("gameId") Integer gameId,
            @RequestParam("gameName") String gameName,
            @RequestParam(value = "gameScore", required = false) Double gameScore,
            @RequestParam("gameDeveloper") String gameDeveloper,
            @RequestParam("releaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,
            @RequestParam("gameDescription") String gameDescription,
            @RequestParam("gameCategories") String gameCategories,
            @RequestParam("gamePlatforms") String gamePlatforms,
            @RequestParam(value = "file", required = false) MultipartFile file) throws JsonProcessingException {

        // 处理 JSON 数组
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> categories = objectMapper.readValue(gameCategories, new TypeReference<>() {
        });
        List<String> platforms = objectMapper.readValue(gamePlatforms, new TypeReference<>() {
        });

        // 构建请求对象
        GameCreateRequest request = new GameCreateRequest();
        request.setGameName(gameName);
        request.setGameScore(gameScore);
        request.setGameDeveloper(gameDeveloper);
        request.setReleaseDate(releaseDate);
        request.setGameDescription(gameDescription);
        request.setGameCategories(categories);
        request.setGamePlatforms(platforms);
        request.setGameId(gameId);

        // 处理文件上传
        if (file != null && !file.isEmpty()) {
            try {
                String uploadFile = minioUtils.pubUploadFile(file);
                uploadFile = pubHandlerUrl + uploadFile;
                request.setGameImageUrl(uploadFile);
                // 查找出此游戏的封面URL
                String oldImageUrl = gameService.getGameImageUrl(gameId);
                // 删除旧的封面图片
                if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    minioUtils.deleteFile(oldImageUrl.replace(pubHandlerUrl, ""));
                }
                gameService.updateGame(request);
                return ResultEntity.success();
            } catch (Exception e) {
                log.error("上传文件失败", e);
                return ResultEntity.serverError();
            }
        }
        return ResultEntity.failure("上传文件失败");
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("delete/by/id")
    public ResultEntity<String> deleteGameInfo(@RequestParam("gameId") Integer gameId) {
        try {
            Game game = gameService.selectById(gameId);
            if (game == null) {
                return ResultEntity.failure("Game not found");
            }
            gameService.deleteGameInfo(gameId);
            if (game.getGameImageUrl() != null && !game.getGameImageUrl().isEmpty()) {
                minioUtils.pubDeleteFile(game.getGameImageUrl().replace(pubHandlerUrl, ""));
                return ResultEntity.success("delete game info success");
            } else {
                throw new RuntimeException("Game image url is empty");
            }
        } catch (RuntimeException e) {
            log.error("删除游戏信息失败", e);
            return ResultEntity.failure(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping("/test")
    public ResultEntity<String> test() {
        return ResultEntity.success("test");
    }
}
