package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") Long postId,@Valid @RequestBody CommentDTO commentDTO)
    {
        return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId")Long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId")Long postId, @PathVariable(value = "id") Long commentId)
    {
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId), HttpStatus.OK);
    }
    @PutMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable(value = "postId")Long postId, @PathVariable(value = "id") Long commentId, @Valid @RequestBody CommentDTO commentDTO)
    {
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId")Long postId, @PathVariable(value = "id") Long commentId)
    {
        return new ResponseEntity<>(commentService.deleteCommentById(postId,commentId), HttpStatus.OK);
    }
}
