package com.x.backend.service.admin;
import java.util.Date;
import java.util.List;

import com.x.backend.pojo.common.TypeCount;

public interface CountService {
    List<Integer> getAllCounts(Date startTime, Date endTime);

    List<Integer> getSongCounts(Date startTime, Date endTime);

    List<Integer> getMovieCounts(Date startTime, Date endTime);

    List<Integer> getBookCounts(Date startTime, Date endTime);

    List<Integer> getGameCounts(Date startTime, Date endTime);

    List<Integer> getOtherCounts(Date startTime, Date endTime);

    List<Integer> getAiCounts(Date startTime, Date endTime);

    void addInsertSongCount(Date date, int count);

    void addUpdateSongCount(Date date, int i);

    void addDeleteSongCount(Date date, int i);

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
