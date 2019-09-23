package com.topGame.entity;

import com.topGame.entity.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Text message;

    @Column(precision = 2,columnDefinition = "double default 0")
    private double rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id")
    private GameObject gameObject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id")
    private User user;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Comment(){}

}
