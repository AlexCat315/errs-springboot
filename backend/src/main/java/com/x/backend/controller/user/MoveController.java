package com.x.backend.controller.user;


import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.responses.move.MoveResponsesVO;
import com.x.backend.service.user.MoveService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RoleSecurity(RoleConstants.ROLE_USER)
@RestController("userMoveController")
@RequestMapping("/api/user/movie")
public class MoveController {


    @Resource(name = "userMoveService")
    private MoveService moveService;


    @PostMapping("/get/all")
    public ResultEntity<List<MoveResponsesVO>> getAllMoves(@RequestBody PageSize pageSize) {
        return moveService.getAllMoves(pageSize);
    }

}
