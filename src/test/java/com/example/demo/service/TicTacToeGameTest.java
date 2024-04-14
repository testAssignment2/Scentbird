package com.example.demo.service;

import com.example.demo.model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeGameTest {

    private TicTacToeGame ticTacToeGame;

    @BeforeEach
    void setUp() {
        ticTacToeGame = new TicTacToeGame();
        ReflectionUtils.findField(TicTacToeGame.class, "currentPlayer").setAccessible(true);
        ReflectionUtils.findMethod(TicTacToeGame.class, "postConstruct").setAccessible(true);
        ReflectionUtils.setField(ReflectionUtils.findField(TicTacToeGame.class, "currentPlayer"), ticTacToeGame, 'X');
        ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(TicTacToeGame.class, "postConstruct"), ticTacToeGame);
    }

    @Test
    void makeMove_ValidMove_ReturnsTrue() {
        // Arrange
        int row = 0;
        int col = 0;

        // Act
        boolean result = ticTacToeGame.makeMove(row, col);

        // Assert
        assertTrue(result);
    }

    @Test
    void makeMove_InvalidMove_ReturnsFalse() {
        // Arrange - Make a valid move first
        ticTacToeGame.makeMove(0, 0);

        // Act - Try making another move at the same position
        boolean result = ticTacToeGame.makeMove(0, 0);

        // Assert
        assertFalse(result);
    }
}
