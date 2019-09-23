package com.topGame.entity;

import com.topGame.validation.ValidationEmail;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false)
    private String username;

    @ValidationEmail
    @Column(name="email",nullable = false)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @CreatedDate
    @Column(name="created_at")
    private Date createdAt;

    @ManyToMany
    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy="user")
    private Set<Comment> commentSet;

    @Column(name = "enabled")
    private boolean enabled;

    public User(){}

}