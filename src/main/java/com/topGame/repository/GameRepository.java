package com.topGame.repository;

import com.topGame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(@Param("name") String name);
}
