package com.x.backend.pojo.user.dto.game;

import lombok.Data;
import java.util.Date;

@Data
public class GameRantingCommentDTO {
    private Integer id;
    private Double score;
    private String comment;
    private Integer userID;
    private Date date;
}
