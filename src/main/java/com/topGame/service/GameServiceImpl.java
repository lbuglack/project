package com.topGame.service;

import com.topGame.entity.Game;
import com.topGame.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game save(Game game) {

        log.info("IN GameServiceImpl save {}", game);

        return gameRepository.saveAndFlush(game);
    }

    @Override
    public List<Game> getAll() {

        log.info("IN GameServiceImpl getAll");

        return gameRepository.findAll();
    }

}
