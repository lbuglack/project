package com.topGame.repository;

import com.topGame.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Comment findById(@Param("id") Long id);

    List<Comment> findByUser_Id(@Param("id") Long id);

    @Query("select comment from Comment comment where comment.post_id=:id")
    List<Comment> findAllPostComments(@Param("id") Long id);

}

