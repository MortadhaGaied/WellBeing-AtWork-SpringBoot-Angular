package com.wellbeignatwork.backend.RestController;

import com.wellbeignatwork.backend.Entity.User;
import lombok.Data;


@Data
public class ConnectRequest {
    private User player;
    private String gameId;
}
