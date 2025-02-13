package com.x.backend.pojo.admin.vo.request.game;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GameCreateRequest {
    private Integer gameId;
    private String gameName;
    private Double gameScore;
    private String gameDeveloper;
    private LocalDate releaseDate;
    private String gameDescription;
    private List<String> gameCategories;
    private List<String> gamePlatforms;
    private String gameImageUrl;
}