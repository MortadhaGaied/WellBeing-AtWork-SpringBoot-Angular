package com.wellbeignatwork.backend.entity.Evaluation;

import com.wellbeignatwork.backend.entity.User.Userr;
import lombok.Data;

import java.io.Serializable;

@Data

public class Game implements Serializable {

    private String gameId;
    private Userr player1;
    private Userr player2;
    private GameStatus status;
    private int[][] board;
    private TicToe winner;
}
