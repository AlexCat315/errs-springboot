package com.x.backend.mapper.user;


import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.common.Song;
import com.x.backend.pojo.user.dto.song.UpdateSongScoreUserDTO;
import com.x.backend.pojo.user.vo.request.song.SearchSongVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userSongMapper")
public interface SongMapper {

    List<Song> selectAll(PageSize pageSize);

    Song selectById(Long id);

    int updateSongScoreUsers(UpdateSongScoreUserDTO updateSongScoreUserDTO);

    int deleteSongScoreUsers(UpdateSongScoreUserDTO updateSongScoreUserDTO);

    int insertSongScoreUser(UpdateSongScoreUserDTO updateSongScoreUserDTO);

    int deleteSongScoreUser(UpdateSongScoreUserDTO updateSongScoreUserDTO);

    Integer isSongLiked(@Param("accountId") Integer accountId, @Param("songId") Long id);

    List<Song> search(SearchSongVO searchSongVO);
}
