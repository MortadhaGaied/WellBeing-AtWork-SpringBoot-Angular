package com.wellbeignatwork.backend.entity;

import lombok.Data;

import java.io.Serializable;

@Data

public class Game implements Serializable {

    private String gameId;
    private User player1;
    private User player2;
    private GameStatus status;
    private int[][] board;
    private TicToe winner;
}
