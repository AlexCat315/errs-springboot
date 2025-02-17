package com.x.backend.pojo.user.dto.movie;

import lombok.Data;

import java.util.Date;

@Data
public class InsertRatingCommentDTO {
    private Integer userId;
    private Long movieId;
    private String comment;
    private Double rating;
    private Date timestamp;
}
