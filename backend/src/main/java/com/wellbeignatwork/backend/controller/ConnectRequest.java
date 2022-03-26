package com.wellbeignatwork.backend.controller;

import com.wellbeignatwork.backend.entity.User;
import lombok.Data;


@Data
public class ConnectRequest {
    private User player;
    private String gameId;
}
