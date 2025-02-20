package com.x.backend.service.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.request.song.SearchSongVO;
import com.x.backend.pojo.user.vo.request.song.UpdateSongScoreUsersVO;
import com.x.backend.pojo.user.vo.responses.song.SongVO;

import java.util.List;

public interface SongService {

    ResultEntity<List<SongVO>> getAll(PageSize pageSize);

    ResultEntity<SongVO> getById(Long id);

    void updateSong(UpdateSongScoreUsersVO updateSongScoreUsersVO);

    ResultEntity<Boolean> getIsLike(Long id);

    ResultEntity<List<SongVO>> search(SearchSongVO searchSongVO);
}
