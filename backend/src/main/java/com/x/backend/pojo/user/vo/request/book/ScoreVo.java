package com.x.backend.pojo.user.vo.request.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScoreVo {
    @NotNull
    private Integer score;
    @NotNull
    @JsonProperty("bId")
    private Long bId;
}
