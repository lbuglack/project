package com.topGame.repository;

import com.topGame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game,Long> {


    @Query("delete from Game game where game.name=:name ")
    void deleteByName(@Param("name") String name);

}
