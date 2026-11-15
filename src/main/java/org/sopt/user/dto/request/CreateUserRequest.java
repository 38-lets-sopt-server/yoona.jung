package org.sopt.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank
        String nickname,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
