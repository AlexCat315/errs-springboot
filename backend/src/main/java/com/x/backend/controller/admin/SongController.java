package com.x.backend.controller.admin;


import com.fasterxml.jackson.core.JsonProcessingException;
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

}
