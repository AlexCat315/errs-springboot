package com.x.backend.task;

import java.util.Date;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.x.backend.mapper.admin.CountMapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CountTask {

    @Resource(name = "adminCountMapper")
    private CountMapper countMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Transactional(rollbackFor = RuntimeException.class)
    @Scheduled(cron = "0 */5 * * * ?")
    public void updateCount() {
        log.info("updateCount start");
        String allCountString = redisTemplate.opsForValue().get("all_access_count");
        Integer allCount = allCountString == null ? 0 : Integer.parseInt(allCountString);
        countMapper.insertAllCount(new Date(), allCount);

        String songCountString = redisTemplate.opsForValue().get("song_access_count");
        Integer songCount = allCountString == null ? 0 : Integer.parseInt(songCountString);
        countMapper.insertSongCount(new Date(), songCount);

        String bookCountString = redisTemplate.opsForValue().get("book_access_count");
        Integer bookCount = allCountString == null ? 0 : Integer.parseInt(bookCountString);
        countMapper.insertBookCount(new Date(), bookCount);

        String gameCountString = redisTemplate.opsForValue().get("game_access_count");
        Integer gameCount = allCountString == null ? 0 : Integer.parseInt(gameCountString);
        countMapper.insertGameCount(new Date(), gameCount);

        String movieCountString = redisTemplate.opsForValue().get("movie_access_count");
        Integer movieCount = allCountString == null ? 0 : Integer.parseInt(movieCountString);
        countMapper.insertMovieCount(new Date(), movieCount);
        
        String outherCountString = redisTemplate.opsForValue().get("outher_access_count");
        Integer outherCount = allCountString == null ? 0 : Integer.parseInt(outherCountString);
        countMapper.insertOtherCount(new Date(), outherCount);

        String AiCountString = redisTemplate.opsForValue().get("ai_access_count");
        Integer AiCount = allCountString == null ? 0 : Integer.parseInt(AiCountString);
        countMapper.insertAiCount(new Date(), AiCount);

        log.info("updateCount end");
    }

}
