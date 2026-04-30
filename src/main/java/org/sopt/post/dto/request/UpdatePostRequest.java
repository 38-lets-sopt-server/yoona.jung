package org.sopt.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePostRequest(
        @Schema(description = "게시글 수정 제목 ", example = "수정된 게시글 제목입니다.")
        String title,

        @Schema(description = "게시글 수정 내용", example = "수정된 게시글 내용입니다.")
        String newContent
) {}
