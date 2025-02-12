package com.x.backend.pojo.user.vo.request.game;

import lombok.Data;

import java.io.Serializable;

@Data
public class GameRantingCommentVO implements Serializable {
    private Integer id;
    private Double score;
    private String comment;
}
