package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.vo.request.song.SearchSongVO;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("adminSongMapper")
public interface SongMapper {

    Integer save(Song song);

    List<Song> search(SearchSongVO searchSongVO);

    List<Song> getAll(PageSize pageSize);

    Song getSongInfoById(Long songId);

    Integer deleteSongInfo(Long songId);

    Integer update(Song song);
}
