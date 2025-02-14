package com.x.backend.service.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import com.x.backend.pojo.user.vo.responses.song.SongVO;

import java.util.List;

public interface SongService {

    ResultEntity<List<SongVO>> getAll(PageSize pageSize);
}
