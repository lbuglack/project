package com.topGame.service;

import com.topGame.entity.Game;

import java.util.List;

public interface GameService {

    Game save(Game game);

    List<Game> getAll();
}

