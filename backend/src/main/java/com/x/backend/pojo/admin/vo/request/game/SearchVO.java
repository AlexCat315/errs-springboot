package com.x.backend.pojo.admin.vo.request.game;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchVO {
    @NotNull
    private String searchText;
    private Integer page;
    private Integer size;
}
