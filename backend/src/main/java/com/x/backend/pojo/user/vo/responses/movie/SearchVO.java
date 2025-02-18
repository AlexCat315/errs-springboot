package com.x.backend.pojo.user.vo.responses.movie;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchVO {
    @NotNull
    private int size = 10;
    @NotNull
    private int page = 1;
    private int start=0;
    private String SearchText;
}
