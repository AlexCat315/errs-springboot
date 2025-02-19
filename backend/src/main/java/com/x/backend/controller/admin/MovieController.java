package com.x.backend.controller.admin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.vo.responses.movie.MovieResponsesVO;
import com.x.backend.pojo.common.Movie;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.MovieService;
import com.x.backend.util.MinioUtils;
import com.x.backend.util.TimeUtils;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RoleSecurity(RoleConstants.ROLE_ADMIN)
@RestController("adminMoveController")
@RequestMapping("/api/admin/movie")
public class MovieController {


    @Resource(name = "adminMoveService")
    private MovieService movieService;
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
            Movie movie = new Movie();

            movie.setName(name);
            movie.setVideo(uploadvideoUrlFile);
            movie.setCover(uploadCoverUrlFile);
            movie.setDirector(director);
            movie.setActor(actor);
            movie.setRating(rating);
            movie.setYear(timeUtils.string2Date(year));
            movie.setLanguage(language);
            movie.setSummary(summary);
            movie.setUsers(users);
            // 使用 Jackson 将 List 转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                movie.setTypes(objectMapper.writeValueAsString(type));
                movieService.createMove(movie);
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

    @PostMapping("/get/info/all")
    public ResultEntity<List<MovieResponsesVO>> getAllMovieInfo(@RequestBody PageSize pageSize) {
        try {
            return movieService.getAllMovieInfo(pageSize);
        } catch (Exception e) {
            log.error("get all movie info error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/get/info/by/id")
    public ResultEntity<MovieResponsesVO> getMovieInfoById(@RequestParam("id") Long id) {
        try {
            return movieService.getMovieInfoById(id);
        } catch (Exception e) {
            log.error("get movie info by id error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/update/info")
    public ResultEntity<String> updateMovieInfo(
            @NotNull(message = "ID不能为空")
            @RequestParam("id") Integer id,

            @NotEmpty(message = "电影名称不能为空")
            @RequestParam("name") String name,

            @NotEmpty(message = "导演不能为空")
            @RequestParam("director") String director,

            // 将文件参数改为可选
            @RequestParam(value = "video", required = false) MultipartFile video,

            @RequestParam(value = "cover", required = false) MultipartFile cover,

            @NotEmpty(message = "演员不能为空")
            @RequestParam("actor") String actor,

            @NotNull(message = "评分不能为空")
            @RequestParam("rating") Double rating,

            @NotEmpty(message = "年份不能为空")
            @RequestParam("year") String year,

            @NotEmpty(message = "语言不能为空")
            @RequestParam("language") String language,

            @NotEmpty(message = "简介不能为空")
            @RequestParam("summary") String summary,

            @NotEmpty(message = "类型不能为空")
            @RequestParam("type") List<String> type,

            @NotNull(message = "用户数不能为空")
            @RequestParam("users") Long users) {
        try {
            // 获取现有电影信息
            Movie existingMovie = movieService.selectById(id);
            if (existingMovie == null) {
                return ResultEntity.failure("Movie not found");
            }

            // 创建新的电影记录
            Movie movie = new Movie();
            movie.setId(id);
            movie.setName(name);
            movie.setDirector(director);
            movie.setActor(actor);
            movie.setRating(rating);
            movie.setYear(timeUtils.string2Date(year));
            movie.setLanguage(language);
            movie.setSummary(summary);
            movie.setUsers(users);

            // 处理类型转换
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                movie.setTypes(objectMapper.writeValueAsString(type));

                // 处理视频文件
                if (video != null && !video.isEmpty()) {
                    // 删除旧文件
                    if (existingMovie.getVideo() != null) {
                        minioUtils.pubDeleteFile(existingMovie.getVideo().replace(pubHandlerUrl, ""));
                    }
                    // 上传新文件
                    String uploadVideoUrlFile = minioUtils.pubUploadFile(video);
                    movie.setVideo(pubHandlerUrl + uploadVideoUrlFile);
                } else {
                    // 保持原有视频URL
                    movie.setVideo(existingMovie.getVideo());
                }

                // 处理封面文件
                if (cover != null && !cover.isEmpty()) {
                    // 删除旧文件
                    if (existingMovie.getCover() != null) {
                        minioUtils.pubDeleteFile(existingMovie.getCover().replace(pubHandlerUrl, ""));
                    }
                    // 上传新文件
                    String uploadCoverUrlFile = minioUtils.pubUploadFile(cover);
                    movie.setCover(pubHandlerUrl + uploadCoverUrlFile);
                } else {
                    // 保持原有封面URL
                    movie.setCover(existingMovie.getCover());
                }

                // 更新电影信息
                movieService.updateMovieInfo(movie);
                return ResultEntity.success("update movie info success");

            } catch (JsonProcessingException e) {
                log.error("转换 List 或 JSON 字符串时发生错误: {}", e.getMessage(), e);
                throw new RuntimeException("转换 List 或 JSON 字符串时发生错误: " + e.getMessage());
            } catch (RuntimeException e) {
                log.error("update movie info error: {}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            log.error("update movie info error: {}", e.getMessage(), e);
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/delete/by/id")
    public ResultEntity<String> deleteMovieInfo(@RequestParam("id") Integer id) {
        try {
            // 获取电影信息
            Movie movie = movieService.selectById(id);
            if (movie == null) {
                return ResultEntity.failure("Movie not found");
            }
            // 删除电影信息
            movieService.deleteMovieInfo(id);

            // 删除视频文件
            if (movie.getVideo() != null) {
                minioUtils.pubDeleteFile(movie.getVideo().replace(pubHandlerUrl, ""));
                return ResultEntity.success("delete movie info success");
            } else {
                return ResultEntity.failure("delete movie info error");
            }
        } catch (Exception e) {
            log.error("delete movie info error: {}", e.getMessage(), e);
            return ResultEntity.failure("delete movie info error");
        }
    }


}

