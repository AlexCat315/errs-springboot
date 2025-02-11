package com.x.backend.pojo.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Game  implements Serializable {
    private Integer id;
    private String gameName;
    private Double gameScore;
    private String gameDeveloper;
    private LocalDate releaseDate;
    private String gameDescription;
    private String gameCategories;
    private String gamePlatforms;
    private String gameImageUrl;
}

