package org.sopt.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreatePostRequest(
        @Schema(description = "게시글 제목 (최대 50자)", example = "제목입니다")
        String title,

        @Schema(description = "게시글 내용 (최대 500자)", example = "내용입니다")
        String content,

        @Schema(description = "사용자 ID", example = "1")
        Long userId
) {}
