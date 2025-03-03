package com.x.backend.mapper.admin;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.x.backend.pojo.common.TypeCount;

@Mapper
@Component("adminCountMapper")
public interface CountMapper {

    void insertAllCount(Date date, int count);

    void insertSongCount(Date date, int count);

    void insertBookCount(Date date, int count);

    void insertGameCount(@Param("date") Date date, @Param("count") Integer gameCount);

    void insertMovieCount(Date date, Integer movieCount);

    List<Integer> getAllCounts(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Integer> getSongCounts(Date startTime, Date endTime);

    List<Integer> getMovieCounts(Date startTime, Date endTime);

    List<Integer> getBookCounts(Date startTime, Date endTime);

    List<Integer> getGameCounts(Date startTime, Date endTime);

    List<Integer> getOtherCounts(Date startTime, Date endTime);

    List<Integer> getAiCounts(Date startTime, Date endTime);

    void insertOtherCount(Date date, Integer outherCount);

    void insertAiCount(Date date, Integer aiCount);

    void addInsertSongCount(Date date, int count);

    void addUpdateSongCount(Date date, int count);

    void addDeleteSongCount(Date date, int count);

    List<Integer> getSongInsertCounts(Date startTime, Date endTime);

    List<Integer> getSongUpdateCounts(Date startTime, Date endTime);

    List<Integer> getSongDeleteCounts(Date startTime, Date endTime);

    List<TypeCount> getSongTypeCounts();

    void addInsertBookCount(Date date, int count);

    void addUpdateBookCount(Date date, int count);

    void addDeleteBookCount(Date date, int count);

    List<Integer> getBookInsertCounts(Date startTime, Date endTime);

    List<Integer> getBookUpdateCounts(Date startTime, Date endTime);

    List<Integer> getBookDeleteCounts(Date startTime, Date endTime);

    List<TypeCount> getBookTypeCounts();

    List<TypeCount> getGamesTypeCounts();
}
