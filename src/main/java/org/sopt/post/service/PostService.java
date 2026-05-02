package org.sopt.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.CustomException;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.CreatePostResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.dto.response.PostSearchResponse;
import org.sopt.post.execption.PostErrorCode;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserErrorCode;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // CREATE
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Post post = new Post(request.title(), request.content(), user);
        postRepository.save(post); // 영속성 컨텍스트에 올라감

        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllWithLikes().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    // READ
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        return postRepository.findById(id)
                .map(PostResponse::from)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
    }

    // SEARCH
    @Transactional(readOnly = true)
    public List<PostSearchResponse> searchPosts(String keyword, String nickname) {
        return postRepository.searchPosts(keyword, nickname).stream()
                .map(PostSearchResponse::from)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        post.update(request.title(), request.newContent());

        return PostResponse.from(post);
    }

    // DELETE
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
    }
}
