package org.sopt.like.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.like.service.LikeService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeFacade {

    private final LikeService likeService;

    public void addLikeWithRetry(Long postId, Long userId) throws InterruptedException {
        int maxAttempt = 50;

        for (int i = 0; i < maxAttempt; i++) {
            try {
                likeService.addLike(postId, userId);
                return;

            } catch (ObjectOptimisticLockingFailureException e) {
                log.info("좋아요 동시성 충돌 발생! 재시도 합니다. (시도 횟수: {})", i + 1);

                Thread.sleep(50);
            }
        }

        throw new RuntimeException("현재 요청이 너무 많습니다. 잠시 후 다시 시도해 주세요.");
    }

    public void cancelLikeWithRetry(Long postId, Long userId) throws InterruptedException {
        int maxAttempt = 50;

        for (int i = 0; i < maxAttempt; i++) {
            try {
                likeService.cancelLike(postId, userId);
                return;

            } catch (ObjectOptimisticLockingFailureException e) {
                log.info("좋아요 취소 동시성 충돌 발생! 재시도 합니다. (시도 횟수: {})", i + 1);

                Thread.sleep(50);
            }
        }

        throw new RuntimeException("현재 요청이 너무 많습니다. 잠시 후 다시 시도해 주세요.");
    }
}
