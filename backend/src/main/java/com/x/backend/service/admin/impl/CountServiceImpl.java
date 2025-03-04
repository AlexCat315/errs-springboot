package com.x.backend.service.admin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.x.backend.mapper.admin.CountMapper;
import com.x.backend.pojo.common.TypeCount;
import com.x.backend.service.admin.CountService;

import jakarta.annotation.Resource;

@Service("adminCountService")
public class CountServiceImpl implements CountService {

    @Resource(name = "adminCountMapper")
    private CountMapper countMapper;

    public List<Integer> getAllCounts(Date startTime, Date endTime) {
        return countMapper.getAllCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getSongCounts(Date startTime, Date endTime) {
        return countMapper.getSongCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getMovieCounts(Date startTime, Date endTime) {
        return countMapper.getMovieCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getBookCounts(Date startTime, Date endTime) {
        return countMapper.getBookCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getGameCounts(Date startTime, Date endTime) {
        return countMapper.getGameCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getOtherCounts(Date startTime, Date endTime) {
        return countMapper.getOtherCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getAiCounts(Date startTime, Date endTime) {
        return countMapper.getAiCounts(startTime, endTime);
    }

    @Override
    public void addInsertSongCount(Date date, int count) {
        countMapper.addInsertSongCount(date, count);
    }

    @Override
    public void addUpdateSongCount(Date date, int count) {
        countMapper.addUpdateSongCount(date, count);
    }

    @Override
    public void addDeleteSongCount(Date date, int count) {
        countMapper.addDeleteSongCount(date, count);
    }

    @Override
    public List<Integer> getSongInsertCounts(Date startTime, Date endTime) {
        return countMapper.getSongInsertCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getSongUpdateCounts(Date startTime, Date endTime) {
        return countMapper.getSongUpdateCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getSongDeleteCounts(Date startTime, Date endTime) {
        return countMapper.getSongDeleteCounts(startTime, endTime);
    }

    @Override
    public List<TypeCount> getSongTypeCounts() {
        List<TypeCount> songTypeCounts = countMapper.getSongTypeCounts();
        return songTypeCounts;
    }

    @Override
    public void addInsertBookCount(Date date, int count) {
        countMapper.addInsertBookCount(date, count);
    }

    @Override
    public void addUpdateBookCount(Date date, int count) {
        countMapper.addUpdateBookCount(date, count);
    }

    @Override
    public void addDeleteBookCount(Date date, int count) {
        countMapper.addDeleteBookCount(date, count);
    }

    @Override
    public List<Integer> getBookInsertCounts(Date startTime, Date endTime) {
        return countMapper.getBookInsertCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getBookUpdateCounts(Date startTime, Date endTime) {
        return countMapper.getBookUpdateCounts(startTime, endTime);
    }

    @Override
    public List<Integer> getBookDeleteCounts(Date startTime, Date endTime) {
        return countMapper.getBookDeleteCounts(startTime, endTime);
    }

    @Override
    public List<TypeCount> getBookTypeCounts() {
        return countMapper.getBookTypeCounts();
    }

    @Override
    public List<TypeCount> getGamesTypeCounts() {
        return countMapper.getGamesTypeCounts();
    }

    @Override
    public Integer getAllCountSong() {
        return countMapper.getAllCountSong();
    }

    @Override
    public Integer getAllCountMovie() {
        return countMapper.getAllCountMovie();
    }

    @Override
    public Integer getAllCountAi() {
        return countMapper.getAllCountAi();
    }

    @Override
    public Integer getAllCountBook() {
        return countMapper.getAllCountBook();
    }

    @Override
    public Integer getAllCountGame() {
         return countMapper.getAllCountGame();
    }

    @Override
    public Integer getAllCountOther() {
        return countMapper.getAllCountOther();
    }

    @Override
    public Integer getAllCountAll() {
        return countMapper.getAllCountAll();
    }

}
