package com.x.backend.mapper.user;

import com.x.backend.pojo.common.Move;
import com.x.backend.pojo.common.PageSize;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userMoveMapper")
public interface MoveMapper {

    List<Move> selectAllMoves(PageSize pageSize);
}
