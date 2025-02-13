package com.x.backend.pojo.admin.dto.game;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchDTO implements Serializable {
    @NotNull
    private String searchText;
    private Integer start;
    private Integer size;
}
