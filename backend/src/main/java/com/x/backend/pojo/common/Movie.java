package com.x.backend.pojo.common;

import lombok.Data;

import java.util.Date;


@Data
public class Movie {
    private Integer id; // 电影ID
    private String name; // 电影名称
    private String director; // 导演
    private String actor; // 演员
    private String types; // 类型
    private String cover; // 封面地址
    private Date year; // 上映年份
    private String language; // 语言
    private Double rating; // 评分
    private Long users; // 评分人数
    private String summary; // 简介
    private String  video; // 视频地址
    private Date createTime; //createTime
    private Date updateTime; //updateTime
}
