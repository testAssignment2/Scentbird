package com.example.demo.model;

import java.util.Arrays;

public class GameState {
    private char[][] board; // Game board
    private char currentPlayer; // Current player (X or O)
    private char lastPlayer; // Last player (X or O)
    private boolean gameOver; // Game over flag
    private char winner; // Winner (X, O, or draw symbol)

    public GameState() {
    }

    // Constructor
    public GameState(char currentPlayer) {
        this.board = new char[3][3]; // Create an empty 3x3 game board
        this.currentPlayer = currentPlayer; // Start player with symbol X
        this.gameOver = false; // Game is not over yet
        this.winner = '-'; // Winner is not determined yet
        initializeBoard(); // Initialize the game board
    }

    // Method to initialize the game board
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-'; // Fill the game board with empty symbols
            }
        }
    }

    // Getters and setters
    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public char getWinner() {
        return winner;
    }

    public void setWinner(char winner) {
        this.winner = winner;
    }

    public char getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(char lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Append current player and game over status
        sb.append("Current Player: ").append(currentPlayer).append("\n");
        sb.append("Game Over: ").append(gameOver).append("\n");

        // Append game board
        sb.append("Game Board:\n");
        if (board != null) {
            for (char[] row : board) {
                sb.append(Arrays.toString(row)).append("\n");
            }
        }

        // Append winner if the game is over
        if (gameOver) {
            sb.append("Winner: ").append(winner).append("\n");
        }

        return sb.toString();
    }
}
