package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.global.common.dto.BaseResponse;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.CreatePostResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "게시글 생성",
            description = "게시글을 생성합니다. "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(@RequestBody CreatePostRequest request) {
        CreatePostResponse response = postService.createPost(request);
       return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("게시글 등록 성공", response));
    }

    @Operation(
            summary = "게시글 전체 조회",
            description = "게시글 전체를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts();
        return ResponseEntity.ok(BaseResponse.success("게시글 전체 조회 성공", response));
    }

    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 ID로 특정 게시글을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "조회할 게시글의 ID", example = "1", required = true)
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(BaseResponse.success("게시글 조회 성공", response));
    }

    @Operation(
            summary = "게시글 수정",
            description = "입력한 ID의 게시글의 제목과 내용을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @Parameter(description = "조회할 게시글의 ID", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request) {
        postService.updatePost(id, request);
        return ResponseEntity.ok(BaseResponse.success("게시글 수정 성공"));
    }

    @Operation(
            summary = "게시글 삭제",
            description = "게시글 ID에 해당하는 게시글을 삭제합니다. "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @Parameter(description = "조회할 게시글의 ID", example = "1", required = true)
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.ok(BaseResponse.success("게시글 삭제 성공"));
    }
}
