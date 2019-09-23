package com.topGame.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "game")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "game")
    private GameObject gameObject;

    public Game() {
    }

    public Game(String name) {
        this.name = name;

    }

}
