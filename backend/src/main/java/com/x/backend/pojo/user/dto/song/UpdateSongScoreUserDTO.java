package com.x.backend.pojo.user.dto.song;

import lombok.Data;

@Data
public class UpdateSongScoreUserDTO {
    private Long songId;
    private Integer accountId;
}
