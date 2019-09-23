package com.topGame.repository;

import com.topGame.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameObjectRepository extends JpaRepository<GameObject,Long> {

    List<GameObject> findByUser_Id(@Param("id") Long id);
    GameObject findById(@Param("id") Long id);
}
