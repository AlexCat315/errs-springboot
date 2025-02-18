package com.x.backend.service.user.impl;

import com.x.backend.mapper.user.BookMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Book;
import com.x.backend.pojo.user.dto.book.CommentDTO;
import com.x.backend.pojo.user.dto.book.IsLikeBook;
import com.x.backend.pojo.user.dto.book.ScoreDTO;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.book.CommentVO;
import com.x.backend.pojo.user.vo.request.book.ScoreVo;
import com.x.backend.pojo.user.vo.request.book.SearchBookVO;
import com.x.backend.service.user.BookService;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component("userBookService")
@Service
public class BookServiceImpl implements BookService {

    @Resource(name = "userBookMapper")
    private BookMapper bookMapper;
    @Resource(name = "adminBookMapper")
    private com.x.backend.mapper.admin.BookMapper adminBookMapper;
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
    public void insertScore(ScoreVo scoreVo) {
        try {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setBId(scoreVo.getBId());
            scoreDTO.setScore(scoreVo.getScore());
            scoreDTO.setAId(jwtUtils.getId());
            Boolean hasKey = redisTemplate.hasKey("book-score-user-" + scoreDTO.getAId() + "-" + scoreDTO.getBId());
            if (hasKey) {
                throw new RuntimeException("您已经评过分了");
            }
            if (bookMapper.validateScore(scoreDTO)) {
                throw new RuntimeException("您已经评过分了");
            }
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
            Boolean hasKey = redisTemplate.hasKey("book-score-user-" + scoreDTO.getAId() + "-" + scoreDTO.getBId());
            if (hasKey) {
                return ResultEntity.failure("您已经评过分了");
            }
            Boolean validated = bookMapper.validateScore(scoreDTO);
            if (validated) {
                return ResultEntity.failure("您已经评过分了");
            }
            return ResultEntity.success();
        } catch (RuntimeException e) {
            log.error("validateScore error", e);
            throw new RuntimeException(e);
        }
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateScore(ScoreVo scoreVo) {
        try {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setBId(scoreVo.getBId());
            scoreDTO.setScore(scoreVo.getScore());
            scoreDTO.setAId(jwtUtils.getId());
            String userId = scoreDTO.getAId().toString();
            Long bookId = scoreVo.getBId();
            // 定义用于存储该书已评分用户的 key，例如："book-score-user-{userId}:{bookId}"
            String userSetKey = "book-score-user-" + userId + "-" + bookId;
            Boolean hasKey = redisTemplate.hasKey(userSetKey);
            if (hasKey) {
                redisTemplate.opsForValue().set(userSetKey, scoreDTO.getScore().toString());
            }
            if (bookMapper.validateScore(scoreDTO)) {
                Integer i = adminBookMapper.deleteBookScore(scoreDTO);

                log.info("i:{}", i);
                if (i != 1) {
                    throw new RuntimeException("更新失败");
                } else {
                    Integer bookUsers = adminBookMapper.updateBookUsers(scoreDTO);
                    log.info("bookUsers:{}", bookUsers);
                    if (bookUsers != 1) {
                        throw new RuntimeException("更新失败");
                    }
                    redisTemplate.opsForValue().set(userSetKey, scoreDTO.getScore().toString());
                }
            }
        } catch (RuntimeException e) {
            log.error("score error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertComment(CommentVO commentVO) {
        try {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(commentVO, commentDTO);
            commentDTO.setUserId((jwtUtils.getId()));
            int i = bookMapper.insertComment(commentDTO);
            if (i != 1) {
                throw new RuntimeException("评论失败");
            }
        } catch (RuntimeException e) {
            log.error("insertComment error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> searchBook(SearchBookVO searchBookVO) {
        try {
            return bookMapper.searchBook(searchBookVO);
        } catch (RuntimeException e) {
            log.error("searchBook error", e);
            throw new RuntimeException(e);
        }

    }
}
