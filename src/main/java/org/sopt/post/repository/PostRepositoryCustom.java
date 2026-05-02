package org.sopt.post.repository;

import org.sopt.post.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> searchPosts(String titleKeyword, String nickname);
}
