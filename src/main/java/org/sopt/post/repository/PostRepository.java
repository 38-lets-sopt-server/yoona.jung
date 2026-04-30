package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT DISTINCT p from Post p LEFT JOIN FETCH p.likes")
    List<Post> findAllWithLikes();

    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.title LIKE %:keyword%")
    List<Post> searchByTitleWithUser(@Param("keyword") String keyword);
}
