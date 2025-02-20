package com.x.backend.pojo.admin.vo.responses.game;


import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class GameResponsesVO {
    private Integer id;
    private String gameName;
    private Double gameScore;
    private String gameDeveloper;
    private LocalDate releaseDate;
    private String gameDescription;
    private List<String> gameCategories;
    private List<String> gamePlatforms;
    private String gameImageUrl;
    private Integer gameUsers;

    private Date createTime;
}