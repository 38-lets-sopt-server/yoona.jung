package org.sopt.like.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.CustomException;
import org.sopt.like.domain.Like;
import org.sopt.like.exception.LikeErrorCode;
import org.sopt.like.repository.LikeRepository;
import org.sopt.post.domain.Post;
import org.sopt.post.execption.PostErrorCode;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserErrorCode;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 좋아요 추가
    @Transactional
    public void addLike(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        if(likeRepository.existsByUserAndPost(user, post)) {
            throw new CustomException(LikeErrorCode.ALREADY_LIKED_POST);
        }

        Like like = new Like(user, post);
        likeRepository.save(like);

        post.increaseLikeCount();
    }

    // 좋아요 취소
    @Transactional
    public void cancelLike(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new CustomException(LikeErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);

        post.decreaseLikeCount();
    }
}
