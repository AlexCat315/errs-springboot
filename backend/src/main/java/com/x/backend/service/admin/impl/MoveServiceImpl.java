package com.x.backend.service.admin.impl;

import com.x.backend.mapper.admin.MoveMapper;
import com.x.backend.pojo.common.Move;
import com.x.backend.service.admin.MoveService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("adminMoveService")
public class MoveServiceImpl implements MoveService {

    @Resource(name = "adminMoveMapper")
    private MoveMapper moveMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void createMove(Move move) {
        try {
            Integer mapperMove = moveMapper.createMove(move);
            if (mapperMove != 1) {
                throw new RuntimeException("create move failed");
            }
        }catch (RuntimeException e){
            log.error("create move failed, cause: {}", e.getMessage());
            throw e;
        }

    }
}
