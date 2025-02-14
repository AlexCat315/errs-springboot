package com.x.backend.service.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.user.SongMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import com.x.backend.pojo.user.vo.responses.song.SongVO;
import com.x.backend.service.user.SongService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userSongService")
public class SongServiceImpl implements SongService {

    @Resource(name = "userSongMapper")
    private SongMapper songMapper;


    @Override
    public ResultEntity<List<SongVO>> getAll(PageSize pageSize) {
        List<SongVO> songVOS = new ArrayList<>();
        try {
            pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
            List<Song> songs = songMapper.selectAll(pageSize);

            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();

            for (Song song : songs) {
                SongVO songVO = new SongVO();
                BeanUtils.copyProperties(song, songVO);
                // 将string类型的artist转换为List<String>
                List<String> songTags = objectMapper.readValue(song.getTags(), new TypeReference<>() {
                });
                songVO.setTags(songTags);
                songVOS.add(songVO);
            }
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException", e);
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            log.error("RuntimeException", e);
            throw e;
        }
        return ResultEntity.success(songVOS);
    }
}
