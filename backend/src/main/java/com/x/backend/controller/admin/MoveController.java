package com.x.backend.controller.admin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Move;
import com.x.backend.service.admin.MoveService;
import com.x.backend.util.MinioUtils;
import com.x.backend.util.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RoleSecurity(RoleConstants.ROLE_ADMIN)
@RestController("adminMoveController")
@RequestMapping("/api/admin/movie")
public class MoveController {


    @Resource(name = "adminMoveService")
    private MoveService moveService;
    @Resource
    private MinioUtils minioUtils;
    @Value("${minio.handler-pub-url}")
    private String pubHandlerUrl;
    @Resource
    private TimeUtils timeUtils;

    @Transactional
    @PostMapping("/create")
    public ResultEntity<String> createMove(@RequestParam("name") String name,
                                           @RequestParam("director") String director,
                                           @RequestParam("actor") String actor,
                                           @RequestParam("video") MultipartFile video,
                                           @RequestParam("cover") MultipartFile cover,
                                           @RequestParam("rating") Double rating,
                                           @RequestParam("year") String year,
                                           @RequestParam("language") String language,
                                           @RequestParam("summary") String summary,
                                           @RequestParam("type") List<String> type,
                                           @RequestParam("users") Long users) throws Exception {
        try {
            // 上传音频文件和封面图到MinIO
            String uploadvideoUrlFile = minioUtils.pubUploadFile(video);
            uploadvideoUrlFile = pubHandlerUrl + uploadvideoUrlFile;
            String uploadCoverUrlFile = pubHandlerUrl + minioUtils.pubUploadFile(cover);

            // 创建电影记录
            Move move = new Move();

            move.setName(name);
            move.setVideo(uploadvideoUrlFile);
            move.setCover(uploadCoverUrlFile);
            move.setDirector(director);
            move.setActor(actor);
            move.setRating(rating);
            move.setYear(timeUtils.string2Date(year));
            move.setLanguage(language);
            move.setSummary(summary);
            move.setUsers(users);
            // 使用 Jackson 将 List 转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                move.setTypes(objectMapper.writeValueAsString(type));
                moveService.createMove(move);
            } catch (JsonProcessingException e) {
                log.error("转换 List 或 JSON 字符串 时发生错误:{}", e.getMessage(), e);
                throw new RuntimeException("转换 List 或 JSON 字符串 时发生错误:" + e.getMessage());
            }
            return ResultEntity.success("create song success");
        } catch (RuntimeException e) {
            log.error("create song error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }


}
