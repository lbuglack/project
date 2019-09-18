package com.topGame.service;

import com.topGame.entity.Comment;
import java.util.List;


public interface CommentService {

    Comment save(Comment comment);
    List<Comment> getAll(Long id);
    List<Comment> getByAuthorId(Long id);
    Comment getById(Long id);
    void delete(Long id);


}
