package com.wellbeignatwork.backend.controller.Evaluation;

import lombok.Data;


@Data
public class ConnectRequest {
    private User player;
    private String gameId;
}
