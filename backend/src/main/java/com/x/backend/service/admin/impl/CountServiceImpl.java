package com.x.backend.service.admin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.x.backend.mapper.admin.CountMapper;
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

}
