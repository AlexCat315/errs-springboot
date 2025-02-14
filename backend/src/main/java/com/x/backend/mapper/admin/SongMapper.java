package com.x.backend.mapper.admin;

import com.x.backend.pojo.common.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("adminSongMapper")
public interface SongMapper {

    Integer save(Song song);
}
