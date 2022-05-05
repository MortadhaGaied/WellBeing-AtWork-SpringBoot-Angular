package com.wellbeignatwork.backend.controller.Evaluation;

import com.wellbeignatwork.backend.entity.User.Userr;
import lombok.Data;


@Data
public class ConnectRequest {
    private Userr player;
    private String gameId;
}
