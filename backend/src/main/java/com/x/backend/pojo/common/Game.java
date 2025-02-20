package com.x.backend.pojo.common;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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
    private Integer gameUsers;
    private Date createTime;
}

