package com.x.backend.pojo.user.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FavoritesGameDTO {
    private Integer gameId;
    private Integer userId;
}
