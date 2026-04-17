package org.sopt.post.service;

import org.sopt.global.exception.PostNotFoundException;
import org.sopt.global.validation.PostValidator;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.response.CreatePostResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final PostValidator postValidator = new PostValidator();

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.postCreateValidation(request);

        LocalDateTime createdAt = java.time.LocalDateTime.now();
        Post post = new Post(postRepository.generateId(), request.title(), request.content(), request.author(), createdAt);
        postRepository.save(post);

        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    // READ
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new PostNotFoundException(id));
        postValidator.PostExistValidation(post, id);

        return new PostResponse(post);
    }

    // UPDATE
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        postValidator.PostExistValidation(post, id);
        post.update(newTitle, newContent);
    }

    // DELETE
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        postValidator.PostExistValidation(post, id);
        postRepository.deleteById(id);
    }
}
