package com.topGame.controller;

import com.topGame.entity.Game;
import com.topGame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //GET /games - в результате поиска должны быть игры
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAll() {

        List<Game> games = this.gameService.getAll();
        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(games, HttpStatus.OK);
    }


    //POST /games – добавить игру
    @PostMapping("/games")
    public ResponseEntity<Game> addGame(@RequestBody Game game) {

        if (game!=null) {
            this.gameService.save(game);
            return new ResponseEntity<>(game, HttpStatus.CREATED);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    //PUT /games/:id – обновить игру
    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@RequestBody Game newGame, @PathVariable Long id) {
        if (newGame != null) {

            this.gameService.save(newGame);
            return new ResponseEntity<>(newGame, HttpStatus.CREATED);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
