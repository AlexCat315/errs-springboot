package com.x.backend.service.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Move;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.pojo.user.vo.responses.move.MoveResponsesVO;

import java.util.List;

public interface MoveService {

    ResultEntity<List<MoveResponsesVO>> getAllMoves(PageSize pageSize);
}
