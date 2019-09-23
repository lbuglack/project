package com.topGame.controller;

import com.topGame.entity.Comment;
import com.topGame.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{id}/comments")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, @PathVariable Long id) {

        if (comment != null) {
            this.commentService.save(comment);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/users/{authorId}/comments/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long authorId, @PathVariable Long id) {

        Comment comment = commentService.delete(id, authorId);
        if (comment != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}/comments")
    public ResponseEntity<List<Comment>> getAllPostComments(@PathVariable Long id) {

        List<Comment> comments = this.commentService.getAll(id);
        if (!comments.isEmpty()) {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{authorId}/comments/{id}")
    public ResponseEntity<Optional<Comment>> getComment(@PathVariable Long authorId, @PathVariable Long id) {

        List<Comment> comments = this.commentService.getByAuthorId(authorId);
        if (!comments.isEmpty()) {
            Optional<Comment> comment = comments.stream().filter(comment1 -> comment1.getId() == id).findFirst();
            if (comment.isPresent()) {
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/articles/{id}/comments")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment newComment, @PathVariable Long id) {

        if (newComment != null) {
            this.commentService.save(newComment);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}