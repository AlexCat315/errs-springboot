package com.x.backend.controller.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.responses.song.SongVO;
import com.x.backend.service.user.SongService;
import com.x.backend.util.MinioUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController("userSongController")
@RequestMapping("/api/user/song")
public class SongController {

    @Resource(name = "userSongService")
    private SongService songService;


    @PostMapping("get/all")
    public ResultEntity<List<SongVO>> getAll(PageSize pageSize) {
        try {
            return songService.getAll(pageSize);
        } catch (RuntimeException e) {
            log.error("get all songs error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/get/by/id")
    public ResultEntity<SongVO> getById(@RequestParam("id") Long id) {
        try {
            return songService.getById(id);
        } catch (RuntimeException e) {
            log.error("get song by id error: {}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

}
