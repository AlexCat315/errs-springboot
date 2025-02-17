package com.x.backend.pojo.user.dto.book;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer userId;
    private String comment;
    private String bookId;
}
