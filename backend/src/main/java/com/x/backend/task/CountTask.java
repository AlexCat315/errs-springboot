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
        final Date now = new Date(); // 统一时间戳

        Integer allCount = getAndResetCount("all_access_count");
        if (allCount > 0) {
            countMapper.insertAllCount(now, allCount);
        }
        Integer songCount = getAndResetCount("song_access_count");
        if (songCount > 0) {
            countMapper.insertSongCount(now, songCount);
        }
        Integer bookCount = getAndResetCount("book_access_count");
        if (bookCount > 0) {
            countMapper.insertBookCount(now, bookCount);
        }
        Integer gameCount = getAndResetCount("game_access_count");
        if (gameCount > 0) {
            countMapper.insertGameCount(now, gameCount);
        }
        Integer movieCount = getAndResetCount("movie_access_count");
        if (movieCount > 0) {
            countMapper.insertMovieCount(now, movieCount);
        }
        Integer otherCount = getAndResetCount("other_access_count");
        if (otherCount > 0) {
            countMapper.insertOtherCount(now, otherCount);
        }
        Integer aiCount = getAndResetCount("ai_access_count");
        if (aiCount > 0) {
            countMapper.insertAiCount(now, aiCount);
        }
        log.info("updateCount end");
    }

    // 使用原子操作获取并重置计数器
    private Integer getAndResetCount(String key) {
        // 原子性操作：获取旧值并设置为0
        String value = redisTemplate.opsForValue().getAndSet(key, "0");

        // 处理可能的null或空值
        if (value == null || value.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.error("Invalid number format for key: {}", key, e);
            return 0;
        }
    }

}
