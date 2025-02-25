package com.x.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.BookTypeCount;
import com.x.backend.pojo.common.Count;
import com.x.backend.pojo.common.SongTypeCount;
import com.x.backend.pojo.common.TimeCount;
import com.x.backend.service.admin.CountService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RoleSecurity(value = RoleConstants.ROLE_ADMIN)
@RestController("adminCountController")
@RequestMapping("api/admin/count")
public class CountController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "adminCountService")
    private CountService countService;

    @PostMapping("/all")
    public ResultEntity<Count> countNumber() {
        Count count = new Count();
        String songCount = redisTemplate.opsForValue().get("song_access_count");
        count.setSongCount(songCount == null ? 0 : Integer.parseInt(songCount));
        String movieCount = redisTemplate.opsForValue().get("movie_access_count");
        count.setMovieCount(movieCount == null ? 0 : Integer.parseInt(movieCount));
        String aiCount = redisTemplate.opsForValue().get("user_access_count");
        count.setAiCount(aiCount == null ? 0 : Integer.parseInt(aiCount));
        String bookCount = redisTemplate.opsForValue().get("book_access_count");
        count.setBookCount(bookCount == null ? 0 : Integer.parseInt(bookCount));
        String gameCount = redisTemplate.opsForValue().get("game_access_count");
        count.setGameCount(gameCount == null ? 0 : Integer.parseInt(gameCount));
        String otherCount = redisTemplate.opsForValue().get("other_access_count");
        count.setOtherCount(otherCount == null ? 0 : Integer.parseInt(otherCount));
        String allCount = redisTemplate.opsForValue().get("all_access_count");
        count.setAllCount(allCount == null ? 0 : Integer.parseInt(allCount));
        return ResultEntity.success(count);
    }

    // 前端传入一个参数，为获取对应的时间端的访问次数（2024-01-01 至 2024-12-31）
    @PostMapping("/all/time")
    public ResultEntity<List<Integer>> countNumberByTime(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        log.info("startTime:{}, endTime:{}", startTime, endTime);
        List<Integer> countList = new ArrayList<>();
        // 调service层，获取对应时间段的访问次数
        countList = countService.getAllCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/song/time")
    public ResultEntity<List<Integer>> countSongByTime(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的歌曲访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getSongCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/movie/time")
    public ResultEntity<List<Integer>> countMovieByTime(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的电影访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getMovieCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("book/time")
    public ResultEntity<List<Integer>> countBookByTime(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的图书访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getBookCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("game/time")
    public ResultEntity<List<Integer>> countGameByTime(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的游戏访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getGameCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("other/time")
    public ResultEntity<List<Integer>> countOtherByTime(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的其他访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getOtherCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("ai/time")
    public ResultEntity<List<Integer>> countAiByTime(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的AI访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getAiCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/song/insert/number")
    public ResultEntity<List<Integer>> songInsertNumber(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的AI访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getSongInsertCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/song/update/number")
    public ResultEntity<List<Integer>> songUpdateNumber(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的AI访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getSongUpdateCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/song/delete/number")
    public ResultEntity<List<Integer>> songDeleteNumber(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的AI访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getSongDeleteCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/song/type/all")
    public ResultEntity<List<SongTypeCount>> countSongType() {
        List<SongTypeCount> countList = countService.getSongTypeCounts();
        return ResultEntity.success(countList);
    }


    @PostMapping("/book/insert/number")
    public ResultEntity<List<Integer>> bookInsertNumber(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的AI访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getBookInsertCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/book/update/number")
    public ResultEntity<List<Integer>> bookUpdateNumber(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的AI访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getBookUpdateCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/book/delete/number")
    public ResultEntity<List<Integer>> bookDeleteNumber(@RequestBody TimeCount timeCount) {
        Date startTime = timeCount.getStartTime();
        Date endTime = timeCount.getEndTime();
        // 调service层，获取对应时间段的AI访问次数
        List<Integer> countList = new ArrayList<>();
        countList = countService.getBookDeleteCounts(startTime, endTime);
        return ResultEntity.success(countList);
    }

    @PostMapping("/book/type/all")
    public ResultEntity<List<BookTypeCount>> countBookType() {
        List<BookTypeCount> countList = countService.getBookTypeCounts();
        return ResultEntity.success(countList);
    }

}
