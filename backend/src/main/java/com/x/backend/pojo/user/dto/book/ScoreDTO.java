package com.x.backend.pojo.user.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
    private Integer aId;
    private Long bId;
    @NotNull
    @Length(min = 1, max = 10)
    private Integer score;

}
