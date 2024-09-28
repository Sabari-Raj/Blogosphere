package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name ="CRUD REST APIs FOR POST Resource"
)
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @SecurityRequirement(
            name ="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create post rest api",
            description = " Create post rest api is used to save post to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 created"
    )
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }
    @GetMapping
    @Operation(
            summary = "Get all post rest api",
            description = " Get all post rest api is used to get all the posts from  database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 created"
    )
    public PostResponse getAllPost(
            @RequestParam(name = "pageNo", defaultValue =DEFAULT_PAGE_NUMBER , required = false ) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name="sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {
            return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
    @Operation(
            summary = "Get post by ID rest api",
            description = " Get post by id rest api is used to get post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name="id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @SecurityRequirement(
            name ="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update post by ID rest api",
            description = " Update post by id rest api is used to update existing post in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"
    )
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO ptdo, @PathVariable(name="id") Long id){
        return ResponseEntity.ok(postService.updatePost(ptdo,id));
    }
    @SecurityRequirement(
            name ="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete post by ID rest api",
            description = " Delete post by id rest api is used to delete existing post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"
    )
    public ResponseEntity<String> deletePost(@PathVariable(name="id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Deleted", HttpStatus.OK);
    }

}
