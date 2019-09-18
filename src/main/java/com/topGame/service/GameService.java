package com.topGame.service;

import com.topGame.entity.Game;

import java.util.List;

public interface GameService {

    Game save(Game game);
    void delete( String name);
    List<Game> getAll();
}

