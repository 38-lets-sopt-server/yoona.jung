package org.sopt.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.global.common.dto.BaseResponse;
import org.sopt.like.dto.LikeRequest;
import org.sopt.like.facade.LikeFacade;
import org.sopt.like.service.LikeService;
import org.sopt.post.dto.response.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 api")
@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeFacade likeFacade;

    @Operation(
            summary = "좋아요 추가",
            description = "게시글 ID에 해당하는 게시글에 좋아요를 추가합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 추가 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시글입니다.")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> addLike(
            @Parameter(description = "좋아요를 누를 게시글의 ID", example = "1", required = true)
            @PathVariable Long postId,
            @RequestBody LikeRequest request
            ) throws InterruptedException{
        likeFacade.addLikeWithRetry(postId, request.userId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("좋아요 추가 성공"));
    }

    @Operation(
            summary = "좋아요 삭제",
            description = "게시글 ID에 해당하는 게시글에 좋아요를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "해당 게시글에서 좋아요를 찾을 수 없습니다.")
    })
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> cancelLike(
            @PathVariable Long postId,
            @RequestBody LikeRequest request
    ) throws InterruptedException {
        likeFacade.cancelLikeWithRetry(postId, request.userId());
        return ResponseEntity.ok(BaseResponse.success("좋아요 취소 성공"));
    }
}
