package com.x.backend.service.admin.impl;

import com.x.backend.mapper.admin.SongMapper;
import com.x.backend.pojo.common.Song;
import com.x.backend.service.admin.SongService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("adminSongService")
public class SongServiceImpl implements SongService {

    @Resource(name = "adminSongMapper")
    private SongMapper songMapper;


    @Override
    public void save(Song song) {
        songMapper.save(song);
    }
}
