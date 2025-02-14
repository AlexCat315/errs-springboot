package com.x.backend.pojo.user.vo.responses.song;

import lombok.Data;

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
}
