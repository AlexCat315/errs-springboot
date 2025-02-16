package com.x.backend.service.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.mapper.user.MoveMapper;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Move;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.responses.move.MoveResponsesVO;
import com.x.backend.service.user.MoveService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userMoveService")
public class MoveServiceImpl implements MoveService {

    @Resource(name = "userMoveMapper")
    private MoveMapper moveMapper;

    @Override
    public ResultEntity<List<MoveResponsesVO>> getAllMoves(PageSize pageSize) {
        try {
            pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
            List<Move> moves = moveMapper.selectAllMoves(pageSize);
            List<MoveResponsesVO> moveResponsesVOS = new ArrayList<>();
            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();
            // 循环遍历 moves，将 Move 对象转换为 MoveResponsesVO 对象
            for (Move move : moves) {
                List<String> types = null;
                MoveResponsesVO moveResponsesVO = new MoveResponsesVO();
                BeanUtils.copyProperties(move, moveResponsesVO);
                try {
                    types = objectMapper.readValue(move.getTypes(), new TypeReference<List<String>>() {
                    });
                } catch (JsonProcessingException e) {
                    log.error("Error parsing gameCategories JSON", e);
                    throw new RuntimeException("Error parsing gameCategories JSON", e);
                }
                moveResponsesVO.setTypes(types);
                moveResponsesVOS.add(moveResponsesVO);
            }
            return ResultEntity.success(moveResponsesVOS);

        } catch (Exception e) {
            log.error("Error getting all moves", e);
            return ResultEntity.failure("Error getting all moves");
        }
    }


}
