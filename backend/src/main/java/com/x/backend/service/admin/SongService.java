package com.x.backend.service.admin;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.vo.request.song.SearchSongVO;
import com.x.backend.pojo.admin.vo.responses.song.SongVO;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;

import java.util.List;

public interface SongService {
    void save(Song song);

    ResultEntity<List<SongVO>> search(SearchSongVO searchSongVO);

    ResultEntity<List<SongVO>> getAll(PageSize pageSize);

    Song getSongInfoById(Long songId);

    void deleteSongInfo(Long songId);


    void update(Song song);
}
