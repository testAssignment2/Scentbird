package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.GameState;
import com.example.demo.service.TicTacToeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final TicTacToeGame ticTacToeGame;
    private final RestTemplate restTemplate;
    @Value("${other.instance.port}")
    private String otherInstancePort;

    @Autowired
    public GameController(TicTacToeGame ticTacToeGame, RestTemplate restTemplate) {
        this.ticTacToeGame = ticTacToeGame;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/start")
    public ResponseEntity<String> startNewGame() {
        ticTacToeGame.reset(); // Start a new game
        updateGameStateOnOtherInstance(); // Notify the other application about the game start
        return ResponseEntity.ok("New game started. You are playing as: " + ticTacToeGame.getCurrentPlayer() +
                "\n" + ticTacToeGame.getGameState().toString());
    }

    @PostMapping("/move")
    public ResponseEntity<String> makeMove(@RequestParam int row, @RequestParam int col) {
        if (ticTacToeGame.makeMove(row, col)) {
            // Notify the other application about the move
            updateGameStateOnOtherInstance();
            String gameState = ticTacToeGame.getGameState().toString();
            if (ticTacToeGame.getGameState().getWinner() != '-') {
                ticTacToeGame.reset();
            }
            return ResponseEntity.ok("Move successful. Current player: " + ticTacToeGame.getCurrentPlayer() +
                    "\n" + gameState);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid move. Please try again.");
        }
    }

    @GetMapping("/reset")
    public ResponseEntity<String> resetGame() {
        ticTacToeGame.reset(); // Reset the game
        updateGameStateOnOtherInstance();
        return ResponseEntity.ok("Game reset." + "\n" + ticTacToeGame.getGameState().toString());
    }

    @PostMapping("/update/GameState")
    public ResponseEntity<Void> getUpdate(@RequestBody GameState gameState) {
        ticTacToeGame.updateGameState(gameState); // Start a new game
        return ResponseEntity.ok().build();
    }

    @GetMapping("/gameState")
    public ResponseEntity<String> getGameState() {
        return ResponseEntity.ok(ticTacToeGame.getGameState().toString());
    }

    private void updateGameStateOnOtherInstance() {
        String otherInstanceUrl = "http://localhost:" + otherInstancePort + "/api/game/update/GameState"; // Assuming the other instance is running on port 8081
        restTemplate.postForEntity(otherInstanceUrl, ticTacToeGame.getGameState(), Void.class);
    }
}


