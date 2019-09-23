package com.topGame.service;

import com.topGame.entity.GameObject;

import java.util.List;


public interface GameObjectService {

    GameObject save(GameObject gameObject);

    void delete(Long id);

    List<GameObject> getAll();

    List<GameObject> getAllMyOwnPosts(Long id);

    GameObject getById(Long id);

}





