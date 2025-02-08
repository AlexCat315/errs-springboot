package com.x.backend.service.user.impl;

import com.x.backend.mapper.user.BookMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.IsLikeBook;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.book.ScoreVo;
import com.x.backend.service.user.BookService;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component("userBookService")
@Service
public class BookServiceImpl implements BookService {

    @Resource(name = "userBookMapper")
    private BookMapper bookMapper;
    @Resource()
    private JWTUtils<UserAccount> jwtUtils;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Book> selectTop250BookInfo(int page, int size) {
        return bookMapper.selectTop250BookInfo(page, size);
    }

    @Override
    public List<Book> selectTop50BookInfo(int page, int size) {
        return bookMapper.selectTop50BookInfo(page, size);
    }

    @Override
    public List<Book> selectTopWelcomeBookInfo(int page, int size) {
        try {
            return bookMapper.selectTopWelcomeBookInfo(page, size);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> selectTopHotBookInfo(int page, int size) {
        try {
            return bookMapper.selectTopHotBookInfo(page, size);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book selectBookDetail(Long id) {
        try {
            return bookMapper.selectBookDetail(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean validateLike(IsLikeBook isLike) {
        try {
            return bookMapper.validateLike(isLike);
        } catch (RuntimeException e) {
            log.error("validateLike error", e);
            throw new RuntimeException(e);
        }
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void score(ScoreVo scoreVo) {
        try {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setBId(scoreVo.getBId());
            scoreDTO.setScore(scoreVo.getScore());
            scoreDTO.setAId(jwtUtils.getId());
            String userId = scoreDTO.getAId().toString();
            Long bookId = scoreVo.getBId();
            // 定义用于存储该书已评分用户的 key，例如："book-score-user-{userId}:{bookId}"
            String userSetKey = "book-score-user-" + userId + "-" + bookId;
            redisTemplate.opsForValue().set(userSetKey, scoreDTO.getScore().toString());
        } catch (RuntimeException e) {
            log.error("score error", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResultEntity<Boolean> validateScore(ScoreDTO scoreDTO) {
        try {
            if (bookMapper.validateScore(scoreDTO)) {
                return ResultEntity.failure("您已经评过分了");
            }
            return ResultEntity.success();
        } catch (RuntimeException e) {
            log.error("validateScore error", e);
            throw new RuntimeException(e);
        }
    }
}
