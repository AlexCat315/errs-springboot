package com.x.backend.controller.user;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.user.vo.request.game.GameRantingCommentVO;
import com.x.backend.pojo.user.vo.responses.game.GameResponsesVO;
import com.x.backend.service.user.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("userGameController")
@RequestMapping("/api/user/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/get/top50")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<GameResponsesVO>> getTop50(@RequestParam(defaultValue = "0") int start) {
        try {
            int size = 25;  // 每页 25 条数据
            return ResultEntity.success(gameService.getTop50(start, size));
        } catch (RuntimeException e) {
            log.error("/get/top50发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/get/highest-rated")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<GameResponsesVO>> getHighestRated(@RequestParam(defaultValue = "0") int start) {
        try {
            int size = 25;  // 每页 25 条数据
            return ResultEntity.success(gameService.getHighestRated(start, size));
        } catch (RuntimeException e) {
            log.error("/get/highest-rated发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/get/most-reviewed")
    @RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
    public ResultEntity<List<GameResponsesVO>> getMostReviewed(@RequestParam(defaultValue = "0") int start) {
        try {
            int size = 25;  // 每页 25 条数据
            return ResultEntity.success(gameService.getMostReviewed(start, size));
        } catch (RuntimeException e) {
            log.error("/get/most-reviewed发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/add/ranting-comment")
    @RoleSecurity(RoleConstants.ROLE_USER)
    public ResultEntity<Boolean> addRantingComment(@RequestBody GameRantingCommentVO gameRantingCommentVO) {
        try {
            return ResultEntity.success(gameService.addRantingComment(gameRantingCommentVO));
        } catch (RuntimeException e) {
            log.error("/add/ranting-comment发生错误:{}", e.getMessage());
            return ResultEntity.failure(e.getMessage());
        }
    }

    @RequestMapping("/test")
    public ResultEntity<String> test() {
        return ResultEntity.success("test");
    }
}
