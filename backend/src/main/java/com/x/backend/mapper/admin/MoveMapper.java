package com.x.backend.mapper.admin;

import com.x.backend.pojo.common.Move;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("adminMoveMapper")
public interface MoveMapper {
    Integer createMove(Move move);
}
