package org.sopt.post.controller;

import org.sopt.global.common.dto.ApiResponse;
import org.sopt.global.exception.PostNotFoundException;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.response.CreatePostResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    @PostMapping
    public ApiResponse<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
        try {
            CreatePostResponse response = postService.createPost(request);
            return ApiResponse.success("게시글 등록 성공", response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail("게시글 등록 실패 " + e.getMessage());
        }
    }

    // GET /posts
    @GetMapping
    public ApiResponse<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts();
        return ApiResponse.success("게시글 전체 조회 성공", response);
    }

    // GET /posts/{id}
    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        try {
            PostResponse response = postService.getPost(id);
            return ApiResponse.success("게시글 조회 성공", response);
        } catch (PostNotFoundException e) {
            return ApiResponse.fail("게시글 조회 실패 " + e.getMessage());
        }
    }

    // PUT /posts/{id}
    @PutMapping("/{id}")
    public ApiResponse<Void> updatePost(Long id, String newTitle, String newContent) {
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success("게시글 수정 성공", null);
        } catch (PostNotFoundException e) {
            return ApiResponse.fail("게시글 수정 실패 " + e.getMessage());
        }
    }

    // DELETE /posts/{id}
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(Long id) {
        try {
            postService.deletePost(id);
            return ApiResponse.success("게시글 삭제 성공", null);
        } catch (PostNotFoundException e) {
            return ApiResponse.fail("게시글 삭제 실패 " + e.getMessage());
        }
    }
}
