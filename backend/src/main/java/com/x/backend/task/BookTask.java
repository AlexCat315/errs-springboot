package com.x.backend.task;


import com.x.backend.mapper.admin.BookMapper;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.dto.book.UpdateBookRatingDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
public class BookTask {
    @Resource(name = "adminBookMapper")
    private BookMapper bookMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate; // 注入StringRedisTemplate


    @Transactional(rollbackFor = RuntimeException.class)
    @Scheduled(cron = "0 */30 * * * ?")
    public void updateBookRating() {
        try {
            // Redis中存入格式为 "book-score-user-{userId}-{bookId}"
            String keyPattern = "book-score-user-*";
            Set<String> keys = getKeysByPrefix(keyPattern);
            if (keys.isEmpty()) {
                log.info("无需要更新的书籍评分");
                return;
            }

            Map<Integer, List<Map<Long, Integer>>> bookScoreMap = new HashMap<>(); // 用户-书籍-评分
            Map<Long, Float[]> bookRatingMap = new HashMap<>(); // 书籍-[评分，评分人数]

            for (String key : keys) {
                String[] keyArr = key.split("-");
                Integer userId = Integer.parseInt(keyArr[3]);
                Long bookId = Long.parseLong(keyArr[4]);
                int score = Integer.parseInt((String) Objects.requireNonNull(redisTemplate.opsForValue().get(key)));

                // 使用 computeIfAbsent 简化
                bookScoreMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(Collections.singletonMap(bookId, score));

                // 计算书籍评分（加权平均）
                bookRatingMap.computeIfAbsent(bookId, k -> new Float[]{0F, 0F});
                Float[] bookData = bookRatingMap.get(bookId);
                bookData[0] = (bookData[0] * bookData[1] + score) / (bookData[1] + 1);
                bookData[1] += 1;
            }

            // 批量更新书籍评分
            List<ScoreDTO> scoreList = new ArrayList<>();
            bookScoreMap.forEach((userId, bookScoreList) ->
                    bookScoreList.forEach(bookScore ->
                            scoreList.add(new ScoreDTO(userId, bookScore.keySet().iterator().next(), bookScore.values().iterator().next()))
                    )
            );

            if (scoreList.isEmpty()) {
                log.info("无需要更新的书籍评分");
                return;
            }

            log.info("更新书籍评分，更新条数：{}", scoreList.size());
            bookMapper.batchInsertScore(scoreList);

            // 更新书籍评分信息
            bookRatingMap.forEach((bookId, bookData) -> {
                UpdateBookRatingDTO updateBookRatingDTO = new UpdateBookRatingDTO();
                updateBookRatingDTO.setBId(bookId);
                updateBookRatingDTO.setRating(bookData[0]);
                updateBookRatingDTO.setUsers(bookData[1].longValue());
                bookMapper.updateBookRatingAndUsers(updateBookRatingDTO);
            });

            // 删除 Redis 键
            redisTemplate.delete(keys);
            log.info("已删除已处理的 Redis 键: {}", keys);
        } catch (RuntimeException e) {
            log.error("updateBookRating error", e);
            throw e;
        }
    }

    /**
     * 获取所有以指定前缀开头的键
     *
     * @param keyPrefix 键名前缀
     * @return 匹配的键集合
     */
    public Set<String> getKeysByPrefix(String keyPrefix) {
        Set<String> keys = new HashSet<>();
        String pattern = keyPrefix + "*";
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(1000).build();

        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            cursor.forEachRemaining(keys::add);
        } catch (RuntimeException e) {
            log.error("getKeysByPrefix error", e);
            throw new RuntimeException(e);
        }
        return keys;
    }


}

