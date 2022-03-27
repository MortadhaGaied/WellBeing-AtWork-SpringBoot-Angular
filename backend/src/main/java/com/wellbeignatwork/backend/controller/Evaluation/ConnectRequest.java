package com.wellbeignatwork.backend.controller.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.User;
import lombok.Data;


@Data
public class ConnectRequest {
    private User player;
    private String gameId;
}
