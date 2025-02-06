package com.x.backend.pojo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeBook implements Serializable  {
    private Integer id;
    private Integer uId;
    private Integer bId;
}
