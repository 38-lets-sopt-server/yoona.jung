package org.sopt.post.service;

import org.sopt.global.exception.PostNotFoundException;
import org.sopt.global.validation.PostValidator;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.response.CreatePostResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final PostValidator postValidator = new PostValidator();

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.PostCreateValidation(request);

        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(postRepository.generateId(), request.title, request.content, request.author, createdAt);
        postRepository.save(post);

        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id);
        postValidator.PostExistValidation(post, id);

        return new PostResponse(post);
    }

    // UPDATE 📝 과제
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id);
        postValidator.PostExistValidation(post, id);
        post.update(newTitle, newContent);
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        Post post = postRepository.findById(id);
        postValidator.PostExistValidation(post, id);
        postRepository.deleteById(id);
    }
}
