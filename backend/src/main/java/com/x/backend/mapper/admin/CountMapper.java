package com.x.backend.mapper.admin;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component("adminCountMapper")
public interface CountMapper {

    void insertAllCount(Date date, int count);

    void insertSongCount(Date date, int count);

    void insertBookCount(Date date, int count);

    @Insert("insert into game_count (date, game_count) values (#{date}, #{gameCount})")
    void insertGameCount(Date date, Integer gameCount);

    void insertMovieCount(Date date, Integer movieCount);

    @Select("SELECT count FROM all_count WHERE date BETWEEN #{startTime} AND #{endTime} ORDER BY date ASC")
    List<Integer> getAllCounts(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("SELECT count FROM song_count WHERE date BETWEEN #{startTime} AND #{endTime} ORDER BY date ASC")
    List<Integer> getSongCounts(Date startTime, Date endTime);

    @Select("SELECT count FROM movie_count WHERE date BETWEEN #{startTime} AND #{endTime} ORDER BY date ASC")
    List<Integer> getMovieCounts(Date startTime, Date endTime);

    @Select("SELECT count FROM book_count WHERE date BETWEEN #{startTime} AND #{endTime} ORDER BY date ASC")
    List<Integer> getBookCounts(Date startTime, Date endTime);

    @Select("SELECT count FROM game_count WHERE date BETWEEN #{startTime} AND #{endTime} ORDER BY date ASC")
    List<Integer> getGameCounts(Date startTime, Date endTime);

    @Select("SELECT count FROM other_count WHERE date BETWEEN #{startTime} AND #{endTime} ORDER BY date ASC")
    List<Integer> getOtherCounts(Date startTime, Date endTime);

    @Select("SELECT count FROM ai_count WHERE date BETWEEN #{startTime} AND #{endTime} ORDER BY date ASC")
    List<Integer> getAiCounts(Date startTime, Date endTime);

    void insertOtherCount(Date date, Integer outherCount);

    void insertAiCount(Date date, Integer aiCount);
}
