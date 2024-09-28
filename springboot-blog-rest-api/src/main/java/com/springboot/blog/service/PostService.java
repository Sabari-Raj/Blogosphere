package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO ptdo);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO ptdo,Long id);
    void deletePost(Long id);
}
