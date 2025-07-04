package com.x.backend.pojo.admin.vo.responses.song;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SongVO {

    private Long id;

    private String name;

    private String artist;

    private String coverUrl;

    private String audioUrl;

    private Double score;

    private List<String> tags;

    private Integer users;

    private Integer likeUsers;

    private Integer dontLikeUsers;
    private Date createTime;
}
