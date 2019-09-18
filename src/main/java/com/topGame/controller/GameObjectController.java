package com.topGame.controller;

import com.topGame.entity.GameObject;
import com.topGame.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameObjectController {

    private final GameObjectService gameObjectService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    //POST /object - Добавить объект
    @PostMapping("/object")
    public ResponseEntity<GameObject> addGameObject(@RequestBody GameObject gameObject) {
        if (gameObject != null) {
            this.gameObjectService.save(gameObject);
            return new ResponseEntity<>(gameObject, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //DELETE /object/:id - удалить объект, удалить может только автор
    @DeleteMapping("/object/{id}")
    public ResponseEntity<GameObject> deleteGameObject(@PathVariable Long id) {
        GameObject gameObject = this.gameObjectService.getById(id);

        if (gameObject!=null) {
            this.gameObjectService.delete(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //PUT /object/:id - Редактирование объекта, может только создатель поста
    @PutMapping("/object/{id}")
    public ResponseEntity<GameObject> updateGameObject(@PathVariable Long id,@RequestBody GameObject newGameObject) {
        if (newGameObject != null) {

            this.gameObjectService.save(newGameObject);
            return new ResponseEntity<>(newGameObject, HttpStatus.CREATED);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //GET /object - получить игровые объекты
    @GetMapping("/objects")
    public ResponseEntity<List<GameObject>> getAll() {

        List<GameObject> gameObjects = this.gameObjectService.getAll();
        if (gameObjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gameObjects, HttpStatus.OK);
    }

    //GET /my - получить список постов авторизованного пользователя
    @GetMapping("/my")
    public ResponseEntity<List<GameObject>> getAllMyPosts(Long id) {

        List<GameObject> gameObjects = this.gameObjectService.getAllMyOwnPosts(id);
        if (gameObjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gameObjects, HttpStatus.OK);
    }


}



