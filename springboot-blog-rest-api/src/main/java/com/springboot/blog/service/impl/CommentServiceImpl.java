package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.BadLocationException;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;
    ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToComment(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        comment.setPost(post);
        return mapToDTO(commentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId,long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
        if(comment.getPost().getId() != postId)
            throw new BlogAPIException("Comment doesn't belong to the post", HttpStatus.BAD_REQUEST);
        return mapToDTO(comment);
    }

    @Override
    public String deleteCommentById(long postId,long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
        if(comment.getPost().getId() != postId)
            throw new BlogAPIException("Comment doesn't belong to the post", HttpStatus.BAD_REQUEST);
        commentRepository.deleteById(commentId);
        return "Comment Deleted Successfully";
    }

    @Override
    public CommentDTO updateCommentById(long postId,long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
        if(comment.getPost().getId() != postId)
            throw new BlogAPIException("Comment doesn't belong to the post", HttpStatus.BAD_REQUEST);
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        return mapToDTO(commentRepository.save(comment));
    }

    private CommentDTO mapToDTO(Comment c)
    {
        CommentDTO commentDTO = mapper.map(c,CommentDTO.class);
        return commentDTO;
    }

    private Comment mapToComment(CommentDTO c)
    {
        Comment comment = mapper.map(c,Comment.class);
        return comment;
    }
}
