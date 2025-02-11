package com.x.backend.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.annotation.RoleSecurity;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.vo.request.game.GameCreateRequest;
import com.x.backend.service.admin.GameService;
import com.x.backend.util.MinioUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping("/test")
    public ResultEntity<String> test() {
        return ResultEntity.success("test");
    }
}
