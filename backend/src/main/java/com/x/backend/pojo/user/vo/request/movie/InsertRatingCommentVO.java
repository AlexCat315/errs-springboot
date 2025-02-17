package com.x.backend.pojo.user.vo.request.movie;

import lombok.Data;

@Data
public class InsertRatingCommentVO {
    private Long movieId;
    private String comment;
    private Double rating;
}
