package com.x.backend.pojo.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PageSize {
    @NotNull
    private int size = 10;
    @NotNull
    private int page = 1;
    private int start=0;
}
