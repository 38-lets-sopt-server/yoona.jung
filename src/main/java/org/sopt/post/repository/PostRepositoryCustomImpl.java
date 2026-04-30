package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.post.domain.Post;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.sopt.post.domain.QPost.post;
import static org.sopt.user.domain.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> searchPosts(String titleKeyword, String nickname) {
        return queryFactory
                .selectFrom(post)
                .join(post.user, user).fetchJoin()
                .where(
                        titleContains(titleKeyword),
                        nicknameEq(nickname)
                )
                .fetch();
    }

    private BooleanExpression titleContains(String titleKeyword) {
        return StringUtils.hasText(titleKeyword) ? post.title.contains(titleKeyword) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return StringUtils.hasText(nickname) ? user.nickname.eq(nickname) : null;
    }
}
