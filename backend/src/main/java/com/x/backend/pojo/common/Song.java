package com.x.backend.pojo.common;

import lombok.Data;
import java.util.Date;


@Data
public class Song {

    private Long id;

    private String name;

    private String artist;

    private String coverUrl;

    private String audioUrl;

    private Double score;

    private String tags;

    private Integer users;

    private Integer likeUsers;

    private Integer dontLikeUsers;
    private Date createTime;
}