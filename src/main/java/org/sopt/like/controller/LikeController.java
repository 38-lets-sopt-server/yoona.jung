package org.sopt.like.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.global.common.dto.BaseResponse;
import org.sopt.like.dto.LikeRequest;
import org.sopt.like.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 api")
@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> addLike(
            @PathVariable Long postId,
            @RequestBody LikeRequest request
            ) {
        likeService.addLike(postId, request.userId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("좋아요 추가 성공"));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> cancelLike(
            @PathVariable Long postId,
            @RequestBody LikeRequest request
    ) {
        likeService.cancelLike(postId, request.userId());
        return ResponseEntity.ok(BaseResponse.success("좋아요 취소 성공"));
    }
}
