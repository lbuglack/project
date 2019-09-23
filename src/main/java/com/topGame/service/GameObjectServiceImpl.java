package com.topGame.service;


import com.topGame.entity.GameObject;
import com.topGame.entity.enums.Status;
import com.topGame.repository.GameObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GameObjectServiceImpl implements GameObjectService {

    private GameObjectRepository gameObjectRepository;

    @Autowired
    public GameObjectServiceImpl(GameObjectRepository gameObjectRepository) {
        this.gameObjectRepository = gameObjectRepository;
    }

    @Override
    public GameObject save(GameObject gameObject) {

        log.info("IN GameObjectServiceImpl save {}", gameObject);
        gameObject.setStatus(Status.INPROCESS);

        return gameObjectRepository.saveAndFlush(gameObject);
    }

    @Override
    public void delete(Long id) {

        log.info("IN GameObjectServiceImpl delete {}", id);

        gameObjectRepository.delete(id);
    }

    @Override
    public List<GameObject> getAll() {

        log.info("IN GameObjectServiceImpl getAll ");

        return gameObjectRepository.findAll();
    }

    @Override
    public List<GameObject> getAllMyOwnPosts(Long id) {

        log.info("IN GameObjectServiceImpl getAllMyPosts {}", id);

        return gameObjectRepository.findByUser_Id(id);
    }

    @Override
    public GameObject getById(Long id) {

        log.info("IN GameObjectServiceImpl getById {}", id);

        return gameObjectRepository.findById(id);
    }
}
