package org.sopt.global.validation;

import org.sopt.global.exception.PostNotFoundException;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;

public class PostValidator {

    public void postCreateValidation(CreatePostRequest request) {
        if (request.title == null || request.title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (request.content == null || request.content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다!");
        }
    }

    public void PostExistValidation(Post post, Long id) {
        if (post == null) {
            throw new PostNotFoundException(id);
        }
    }
}
