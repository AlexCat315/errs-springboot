package com.x.backend.pojo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Book implements Serializable {
    private Long id; //书籍id
    private String name; //书籍名称
    private String author; //书籍作者
    private String description; //编辑评语
    private Float rating; //书籍评分
    private Long users; //书籍用户数
    private String img; //书籍封面
    private String introduction; //书籍简介
    private Double recommend; //推荐度
    private Date createTime; //创建时间
}
