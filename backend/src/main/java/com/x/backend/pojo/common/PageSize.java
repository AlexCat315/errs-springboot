package com.x.backend.pojo.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PageSize {
    @NotNull
    private int size;
    @NotNull
    private int page;
    private int start;
}
