package com.x.backend.service.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.user.SongMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import com.x.backend.pojo.user.dto.song.UpdateSongScoreUserDTO;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.song.SearchSongVO;
import com.x.backend.pojo.user.vo.request.song.UpdateSongScoreUsersVO;
import com.x.backend.pojo.user.vo.responses.song.SongVO;
import com.x.backend.service.user.SongService;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userSongService")
public class SongServiceImpl implements SongService {

    @Resource(name = "userSongMapper")
    private SongMapper songMapper;
    @Resource()
    private JWTUtils<UserAccount> userAccountJWTUtils;


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

    @Override
    public ResultEntity<SongVO> getById(Long id) {
        Song song = songMapper.selectById(id);
        if (song == null) {
            return ResultEntity.failure("Song not found");
        }
        SongVO songVO = new SongVO();
        BeanUtils.copyProperties(song, songVO);
        // 将string类型的artist转换为List<String>
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> songTags;
        try {
            songTags = objectMapper.readValue(song.getTags(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException", e);
            throw new RuntimeException(e);
        }
        songVO.setTags(songTags);
        return ResultEntity.success(songVO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateSong(UpdateSongScoreUsersVO updateSongScoreUsersVO) {
        try {
            Integer id = userAccountJWTUtils.getId();
            UpdateSongScoreUserDTO updateSongScoreUserDTO = new UpdateSongScoreUserDTO();
            updateSongScoreUserDTO.setAccountId(id);
            updateSongScoreUserDTO.setSongId(updateSongScoreUsersVO.getId());
            if (updateSongScoreUsersVO.getIsLiked()) {
                int i = songMapper.updateSongScoreUsers(updateSongScoreUserDTO);
                if (i == 1) {
                    int insertSongScoreUser = songMapper.insertSongScoreUser(updateSongScoreUserDTO);
                    if (insertSongScoreUser != 1) {
                        throw new RuntimeException("insert song score user failed");
                    }
                } else {
                    throw new RuntimeException("update song score user failed");
                }

            } else {
                int i = songMapper.deleteSongScoreUsers(updateSongScoreUserDTO);
                if (i == 1) {
                    int deleteSongScoreUser = songMapper.deleteSongScoreUser(updateSongScoreUserDTO);
                    if (deleteSongScoreUser != 1) {
                        throw new RuntimeException("delete song score user failed");
                    }
                } else {
                    throw new RuntimeException("delete song score user failed");
                }
            }
        } catch (RuntimeException e) {
            log.error("RuntimeException", e);
            throw e;
        }
    }

    @Override
    public ResultEntity<Boolean> getIsLike(Long id) {
        try {
            Integer accountId = userAccountJWTUtils.getId();
            Integer songLiked = songMapper.isSongLiked(accountId, id);
            if (songLiked == 1) {
                return ResultEntity.success(true);
            } else {
                return ResultEntity.success(false);
            }
        } catch (RuntimeException e) {
            log.error("RuntimeException", e);
            throw e;
        }
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

}
