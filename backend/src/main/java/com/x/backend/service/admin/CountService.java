package com.x.backend.service.admin;
import java.util.Date;
import java.util.List;

public interface CountService {
    List<Integer> getAllCounts(Date startTime, Date endTime);

    List<Integer> getSongCounts(Date startTime, Date endTime);

    List<Integer> getMovieCounts(Date startTime, Date endTime);

    List<Integer> getBookCounts(Date startTime, Date endTime);

    List<Integer> getGameCounts(Date startTime, Date endTime);

    List<Integer> getOtherCounts(Date startTime, Date endTime);

    List<Integer> getAiCounts(Date startTime, Date endTime);
}
