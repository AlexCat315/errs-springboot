package com.x.backend.pojo.user.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class IsLikeBook implements Serializable  {
    private Long bId;
    private int aId;
}
