package com.x.backend.mapper.user;


import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userSongMapper")
public interface SongMapper {

    List<Song> selectAll(PageSize pageSize);

    Song selectById(Long id);
}
