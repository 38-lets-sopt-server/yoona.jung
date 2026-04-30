package org.sopt.like.repository;

import org.sopt.like.domain.Like;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // 중복 좋아요 방지
    boolean existsByUserAndPost(User user, Post post);

    // 좋아요 취소를 위해 특정 유저와 게시글 조합의 좋아요 엔티티 찾기
    Optional<Like> findByUserAndPost(User user, Post post);
}
