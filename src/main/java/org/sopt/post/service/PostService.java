package org.sopt.post.service;

import org.sopt.global.exception.PostNotFoundException;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.CreatePostResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        LocalDateTime createdAt = java.time.LocalDateTime.now();
        Post post = new Post(postRepository.generateId(), request.title(), request.content(), request.author(), createdAt);
        postRepository.save(post);

        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ
    public List<PostResponse> getAllPosts() {

        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    // READ
    public PostResponse getPost(Long id) {
        return postRepository.findById(id)
                .map(PostResponse::new)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    // UPDATE
    public void updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        post.update(request.title(), request.newContent());
    }

    // DELETE
    public void deletePost(Long id) {
        if (!postRepository.deleteById(id)) {
            throw new PostNotFoundException(id);
        }
    }
}
