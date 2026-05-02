package org.sopt.post.dto.response;

import org.sopt.post.domain.Post;

public record PostSearchResponse(
        Long id,
        String title,
        String authorNickname,
        int likeCount
) {
    public static PostSearchResponse from(Post post) {
        return new PostSearchResponse(
                post.getId(),
                post.getTitle(),
                post.getUser().getNickname(),
                post.getLikeCount()
        );
    }
}
