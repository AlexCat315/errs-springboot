package com.x.backend.pojo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    private Long id;
    private String name;
    private String author;
    private String description;
    private Float rating;
    private Long users;
    private String img;
    private String introduction;
}
