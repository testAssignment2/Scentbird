package com.example.demo.controller;

import com.example.demo.model.GameState;
import com.example.demo.service.TicTacToeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GameControllerTest {

    @InjectMocks
    private GameController gameController;
    @Mock
    private TicTacToeGame ticTacToeGameMock;
    @Mock
    private RestTemplate restTemplateMock;

    @BeforeEach
    void setUp() {
        when(restTemplateMock.postForEntity(any(), any(), any())).thenReturn(ResponseEntity.ok().build());
    }

    @Test
    void testStartNewGame() {
        // Given
        when(ticTacToeGameMock.getCurrentPlayer()).thenReturn('X');
        when(ticTacToeGameMock.getGameState()).thenReturn(new GameState());

        // When
        ResponseEntity<String> response = gameController.startNewGame();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("New game started"));
        verify(ticTacToeGameMock).reset();
        verify(restTemplateMock).postForEntity(anyString(), any(GameState.class), eq(Void.class));
    }

    @Test
    void testMakeMove_ValidMove() {
        // Given
        int row = 0;
        int col = 0;
        when(ticTacToeGameMock.makeMove(row, col)).thenReturn(true);
        when(ticTacToeGameMock.getCurrentPlayer()).thenReturn('X');
        when(ticTacToeGameMock.getGameState()).thenReturn(new GameState());

        // When
        ResponseEntity<String> response = gameController.makeMove(row, col);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Move successful"));
        verify(restTemplateMock).postForEntity(anyString(), any(GameState.class), eq(Void.class));
    }

    @Test
    void testMakeMove_InvalidMove() {
        // Given
        int row = 0;
        int col = 0;
        when(ticTacToeGameMock.makeMove(row, col)).thenReturn(false);

        // When
        ResponseEntity<String> response = gameController.makeMove(row, col);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid move"));
        verify(restTemplateMock, never()).postForEntity(anyString(), any(GameState.class), eq(Void.class));
    }

    // Add more test cases for other methods as needed
}