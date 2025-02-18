package com.x.backend.pojo.user.vo.request.game;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchGameVO {
    @NotNull
    private int size = 10;
    @NotNull
    private int page = 1;
    private int start=0;
    private String SearchText;
}
