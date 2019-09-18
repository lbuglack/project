package com.topGame.entity;

import com.topGame.entity.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "game_object")
@Data
public class GameObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="description",nullable = false)
    private Text description;

    @Column(precision = 2,columnDefinition = "double default 0")
    private double rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id", nullable=false)
    private User user;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @OneToMany(fetch = FetchType.LAZY,mappedBy="gameObject")
    private Set<Comment> commentSet;

    public GameObject(){}

}