package com.x.backend.service.admin.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.admin.SongMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import com.x.backend.pojo.admin.vo.request.song.SearchSongVO;
import com.x.backend.pojo.admin.vo.responses.song.SongVO;
import com.x.backend.service.admin.SongService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("adminSongService")
public class SongServiceImpl implements SongService {

    @Resource(name = "adminSongMapper")
    private SongMapper songMapper;


    @Override
    public void save(Song song) {
        songMapper.save(song);
    }

    @Override
    public ResultEntity<List<SongVO>> search(SearchSongVO searchSongVO) {
        List<SongVO> songVOS = new ArrayList<>();
        searchSongVO.setStart((searchSongVO.getPage() - 1) * searchSongVO.getSize());
        try {
            List<Song> songs = songMapper.search(searchSongVO);

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

    @Override
    public ResultEntity<List<SongVO>> getAll(PageSize pageSize) {
        try {
            pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
            List<Song> songs = songMapper.getAll(pageSize);
            List<SongVO> songVOS = new ArrayList<>();
            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();
            for (Song song : songs) {
                SongVO songVO = new SongVO();
                BeanUtils.copyProperties(song, songVO);
                songVO.setTags(objectMapper.readValue(song.getTags(), new TypeReference<>() {
                }));
                songVOS.add(songVO);
            }
            return ResultEntity.success(songVOS);
        } catch (JsonMappingException e) {
            log.error("JsonMappingException", e);
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException", e);
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            log.error("RuntimeException", e);
            throw e;
        }
    }

    @Override
    public Song getSongInfoById(Long songId) {
        return songMapper.getSongInfoById(songId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteSongInfo(Long songId) {
        try {
            int i = songMapper.deleteSongInfo(songId);
            if (i != 1) {
                throw new RuntimeException("删除歌曲失败");
            }
        } catch (RuntimeException e) {
            log.error("删除歌曲失败：{}」", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void update(Song song) {
      Integer i =  songMapper.update(song);
    }
}
