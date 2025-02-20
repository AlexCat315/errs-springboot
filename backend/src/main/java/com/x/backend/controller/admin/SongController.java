package com.x.backend.controller.admin;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.annotation.RoleSecurity;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import com.x.backend.pojo.admin.vo.request.song.SearchSongVO;
import com.x.backend.pojo.admin.vo.responses.song.SongVO;
import com.x.backend.service.admin.SongService;
import com.x.backend.util.MinioUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Slf4j
@RoleSecurity(value = {"admin"})
@RestController("adminSongController")
@RequestMapping("/api/admin/song")
public class SongController {

    @Resource(name = "adminSongService")
    private SongService songService;

    @Resource
    private MinioUtils minioUtils;
    @Value("${minio.handler-pub-url}")
    private String pubHandlerUrl;


    @Transactional
    @PostMapping("/create")
    public ResultEntity<String> createSong(@RequestParam("name") String name,
                                           @RequestParam("artist") String artist,
                                           @RequestParam("audio") MultipartFile audio,
                                           @RequestParam("cover") MultipartFile cover,
                                           @RequestParam("score") Double score,
                                           @RequestParam("tags") List<String> tags,
                                           @RequestParam("users") Integer users) throws Exception {
        try {
            // 上传音频文件和封面图到MinIO
            String uploadAudioUrlFile = minioUtils.pubUploadFile(audio);
            uploadAudioUrlFile = pubHandlerUrl + uploadAudioUrlFile;
            String uploadCoverUrlFile = pubHandlerUrl + minioUtils.pubUploadFile(cover);

            // 创建歌曲记录
            Song song = new Song();
            song.setName(name);
            song.setArtist(artist);
            song.setAudioUrl(uploadAudioUrlFile);
            song.setCoverUrl(uploadCoverUrlFile);
            song.setScore(score);
            song.setCreateTime(new Date());
            // 使用 Jackson 将 List 转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将 List 转换为 JSON 字符串
                song.setTags(objectMapper.writeValueAsString(tags));
            } catch (JsonProcessingException e) {
                log.error("转换 List 或 JSON 字符串 时发生错误:{}", e.getMessage(), e);
            }

            song.setUsers(users);
            songService.save(song);
            return ResultEntity.success("create song success");
        } catch (RuntimeException e) {
            log.error("create song error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/update")
    public ResultEntity<String> updateSong(@RequestParam("id") Long id,
                                           @RequestParam("name") String name,
                                           @RequestParam("artist") String artist,
                                           @RequestParam(value = "audio", required = false) MultipartFile audio,
                                           @RequestParam(value = "cover", required = false) MultipartFile cover,
                                           @RequestParam("score") Double score,
                                           @RequestParam("tags") List<String> tags,
                                           @RequestParam("users") Integer users) throws Exception {
        try {
            // 获取现有歌曲信息
            Song song = songService.getSongInfoById(id);
            if (song == null) {
                return ResultEntity.failure("歌曲不存在");
            }

            // 更新基本信息
            song.setName(name);
            song.setArtist(artist);
            song.setScore(score);

            // 如果有新的音频文件，则更新
            if (audio != null && !audio.isEmpty()) {
                String uploadAudioUrlFile = minioUtils.pubUploadFile(audio);
                uploadAudioUrlFile = pubHandlerUrl + uploadAudioUrlFile;
                // 删除原有文件
                String oldAudioUrl = song.getAudioUrl();
                if (oldAudioUrl != null && oldAudioUrl.startsWith(pubHandlerUrl)) {
                    String oldFileName = oldAudioUrl.substring(pubHandlerUrl.length());
                    try {
                        minioUtils.pubDeleteFile(oldFileName);
                    } catch (Exception e) {
                        log.error("删除原音频文件失败: {}", e.getMessage());
                    }
                }
                song.setAudioUrl(uploadAudioUrlFile);
            }

            // 如果有新的封面图片，则更新
            if (cover != null && !cover.isEmpty()) {
                String uploadCoverUrlFile = pubHandlerUrl + minioUtils.pubUploadFile(cover);
                // 删除原有文件
                String oldCoverUrl = song.getCoverUrl();
                if (oldCoverUrl != null && oldCoverUrl.startsWith(pubHandlerUrl)) {
                    String oldFileName = oldCoverUrl.substring(pubHandlerUrl.length());
                    try {
                        minioUtils.pubDeleteFile(oldFileName);
                    } catch (Exception e) {
                        log.error("删除原封面文件失败: {}", e.getMessage());
                    }
                }
                song.setCoverUrl(uploadCoverUrlFile);
            }

            // 更新标签
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                song.setTags(objectMapper.writeValueAsString(tags));
            } catch (JsonProcessingException e) {
                log.error("转换标签为JSON字符串时发生错误: {}", e.getMessage(), e);
                return ResultEntity.failure("标签格式转换失败");
            }

            song.setUsers(users);

            // 保存更新
            songService.update(song);
            return ResultEntity.success("更新歌曲成功");
        } catch (Exception e) {
            log.error("更新歌曲失败: {}", e.getMessage(), e);
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/get/search")
    public ResultEntity<List<SongVO>> search(@RequestBody SearchSongVO searchSongVO) {
        try {
            return songService.search(searchSongVO);
        } catch (RuntimeException e) {
            log.error("search songs error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/get/info/all")
    public ResultEntity<List<SongVO>> getAll(@RequestBody PageSize pageSize) {
        try {
            return songService.getAll(pageSize);
        } catch (RuntimeException e) {
            log.error("get all songs error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/delete/by/id")
    public ResultEntity<String> deleteSongInfo(@RequestParam("musicId") Long songId) {
        try {
            Song song = songService.getSongInfoById(songId);
            songService.deleteSongInfo(songId);
            try {
                minioUtils.pubDeleteFile(song.getCoverUrl().replace(pubHandlerUrl, ""));
                minioUtils.pubDeleteFile(song.getAudioUrl().replace(pubHandlerUrl, ""));
            } catch (Exception e) {
                log.error("删除歌曲信息失败", e);
                throw new RuntimeException(e);
            }
            return ResultEntity.success("删除成功");
        } catch (RuntimeException e) {
            log.error("RuntimeException", e);
            throw e;
        }
    }


    @PostMapping("/get/info/by/id")
    public ResultEntity<SongVO> getSongInfoById(@RequestParam("musicId") Long songId) {
        try {
            Song song = songService.getSongInfoById(songId);
            if (song == null) {
                return ResultEntity.failure("歌曲不存在");
            }
            SongVO songVO = new SongVO();
            songVO.setId(song.getId());
            songVO.setName(song.getName());
            songVO.setArtist(song.getArtist());
            songVO.setScore(song.getScore());
            songVO.setAudioUrl(song.getAudioUrl());
            songVO.setCoverUrl(song.getCoverUrl());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                songVO.setTags(objectMapper.readValue(song.getTags(), new TypeReference<>() {
                }));
            } catch (JsonProcessingException e) {
                log.error("JsonProcessingException", e);
                throw new RuntimeException(e);
            }
            songVO.setUsers(song.getUsers());
            return ResultEntity.success(songVO);
        } catch (RuntimeException e) {
            log.error("get song info error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

}
