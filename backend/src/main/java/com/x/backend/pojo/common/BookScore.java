package com.x.backend.pojo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookScore implements Serializable {
    private Integer id;
    private Integer aId;
    private Long bId;
    private Float score;
}
