package com.example.demo.service;

import com.example.demo.model.GameState;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TicTacToeGame {

    @Value("${current.player:X}")
    private char currentPlayer;

    private GameState gameState;

    @PostConstruct
    private void postConstruct(){
        this.gameState = new GameState(currentPlayer);
    }

    // Method to make a move
    public boolean makeMove(int row, int col) {
        if (gameState.getCurrentPlayer() == gameState.getLastPlayer()) {
            return false; // Player tries to make a move twice in a row
        }
        // Check if the move is valid
        if (isValidMove(row, col)) {
            // Set the symbol of the current player on the selected position
            gameState.getBoard()[row][col] = gameState.getCurrentPlayer();

            // Check if there is a winner after the move
            if (checkForWinner()) {
                gameState.setGameOver(true);
                gameState.setWinner(gameState.getCurrentPlayer());
            } else if (checkForDraw()) { // Check for a draw
                gameState.setGameOver(true);
                gameState.setWinner('-'); // '-' symbol represents a draw
            }
            // Update lastPlayer after a successful move
            gameState.setLastPlayer(gameState.getCurrentPlayer());
            return true; // Move successfully made
        } else {
            return false; // Invalid move
        }
    }

    // Method to check the validity of a move
    private boolean isValidMove(int row, int col) {
        // Check if the selected position on the game board is empty
        return gameState.getBoard()[row][col] == '-';
    }

    // Method to check for a winner
    private boolean checkForWinner() {
        // Check rows, columns, and diagonals for matching symbols
        for (int i = 0; i < 3; i++) {
            if (gameState.getBoard()[i][0] != '-' &&
                    gameState.getBoard()[i][0] == gameState.getBoard()[i][1] &&
                    gameState.getBoard()[i][0] == gameState.getBoard()[i][2]) {
                return true; // Winner in the row
            }
            if (gameState.getBoard()[0][i] != '-' &&
                    gameState.getBoard()[0][i] == gameState.getBoard()[1][i] &&
                    gameState.getBoard()[0][i] == gameState.getBoard()[2][i]) {
                return true; // Winner in the column
            }
        }
        if (gameState.getBoard()[0][0] != '-' &&
                gameState.getBoard()[0][0] == gameState.getBoard()[1][1] &&
                gameState.getBoard()[0][0] == gameState.getBoard()[2][2]) {
            return true; // Winner in the main diagonal
        }
        if (gameState.getBoard()[0][2] != '-' &&
                gameState.getBoard()[0][2] == gameState.getBoard()[1][1] &&
                gameState.getBoard()[0][2] == gameState.getBoard()[2][0]) {
            return true; // Winner in the secondary diagonal
        }
        return false; // No winner
    }

    // Method to check for a draw
    private boolean checkForDraw() {
        // Check if all cells are filled and there is no winner
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState.getBoard()[i][j] == '-') {
                    return false; // Not all cells are filled
                }
            }
        }
        return true; // All cells are filled, draw
    }

    // Method to reset the game to its initial state
    public void reset() {
        this.gameState = new GameState(currentPlayer);
    }

    // Method to get the current game state
    public GameState getGameState() {
        return gameState;
    }

    // Method to get the current player
    public char getCurrentPlayer() {
        return gameState.getCurrentPlayer();
    }

    // Method to update the game state
    public void updateGameState(GameState gameState) {
        this.gameState = gameState;
        this.gameState.setCurrentPlayer(currentPlayer);
    }
}
