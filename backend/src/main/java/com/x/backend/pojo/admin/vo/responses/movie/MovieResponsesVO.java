package com.x.backend.pojo.admin.vo.responses.movie;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MovieResponsesVO {
    private Integer id; // 电影ID
    private String name; // 电影名称
    private String director; // 导演
    private String actor; // 演员
    private List<String> types; // 类型
    private String cover; // 封面地址
    private Date year; // 上映年份
    private String language; // 语言
    private Double rating; // 评分
    private Long users; // 评分人数
    private String summary; // 简介
    private String  video; // 视频地址
    private Date createTime;
}
