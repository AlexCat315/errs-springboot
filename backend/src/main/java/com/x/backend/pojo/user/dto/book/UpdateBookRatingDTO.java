package com.x.backend.pojo.user.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookRatingDTO {
    private Long bId;
    private Float rating;
    private Long users;
}
