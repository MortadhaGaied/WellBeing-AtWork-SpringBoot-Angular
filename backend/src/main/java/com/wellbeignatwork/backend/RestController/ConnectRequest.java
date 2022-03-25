package com.wellbeignatwork.backend.RestController;

import lombok.Data;
import tn.pidev.spring.version0backend.Entity.User;

@Data
public class ConnectRequest {
    private User player;
    private String gameId;
}
