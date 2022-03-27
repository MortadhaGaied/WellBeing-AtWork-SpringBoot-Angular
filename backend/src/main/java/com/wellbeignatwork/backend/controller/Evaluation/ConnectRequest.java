package com.wellbeignatwork.backend.controller.Evaluation;

import lombok.Data;
import com.wellbeignatwork.backend.entity.User;


@Data
public class ConnectRequest {
    private User player;
    private String gameId;
}
