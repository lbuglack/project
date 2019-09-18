package com.topGame.service;

import com.topGame.entity.Comment;
import com.topGame.entity.enums.Status;
import com.topGame.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        log.info("IN CommentServiceImpl save {}", comment);
        comment.setStatus(Status.INPROCESS);
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public List<Comment> getAll(Long id) {
        log.info("IN CommentServiceImpl getAll {}", id);
        return commentRepository.findAllPostComments(id);
    }

    @Override
    public void delete(Long id) {
        log.info("IN CustomerServiceImpl delete {}", id);
        commentRepository.delete(id);
    }

    @Override
    public List<Comment> getByAuthorId(Long id) {
        log.info("IN CommentServiceImpl getByAuthorId {}", id);
        return  commentRepository.findByUser_Id(id);
    }

    @Override
    public Comment getById(Long id) {
        log.info("IN CommentServiceImpl getById {}", id);
        return  commentRepository.findById(id);
    }

}
