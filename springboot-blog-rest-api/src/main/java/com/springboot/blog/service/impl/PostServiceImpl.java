package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDTO createPost(PostDTO ptdo) {
        Post save = postRepository.save(mapToPost(ptdo));
        return maptoDTO(save);
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable  page = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(page);
        List<Post> listPost = posts.getContent();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(listPost.stream().map(this::maptoDTO).collect(Collectors.toList()));
        postResponse.setPageSize(posts.getSize());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements((int) posts.getTotalElements());
        postResponse.setLast(posts.isLast());


        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        return maptoDTO(postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id)));
    }

    @Override
    public PostDTO updatePost(PostDTO ptdo,Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        Post newUpdatedPost = mapToPost(ptdo);
        post.setTitle(newUpdatedPost.getTitle());
        post.setDescription(newUpdatedPost.getDescription());
        post.setContent(newUpdatedPost.getContent());
        return maptoDTO(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.deleteById(id);
    }

    private PostDTO maptoDTO(Post post)
    {
        PostDTO postReturn = mapper.map(post, PostDTO.class);
        return postReturn;
    }
    private Post mapToPost(PostDTO ptdo)
    {
        Post post = mapper.map(ptdo,Post.class);
        return post;
    }
}
