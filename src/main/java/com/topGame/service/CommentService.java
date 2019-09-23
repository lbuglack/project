package com.topGame.service;

import com.topGame.entity.Comment;
import java.util.List;


public interface CommentService {

    Comment save(Comment comment);
    List<Comment> getAll(Long id);
    List<Comment> getByAuthorId(Long id);
    Comment delete(Long id,Long userId);

}
