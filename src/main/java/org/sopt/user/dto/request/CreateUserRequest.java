package org.sopt.user.dto.request;

public record CreateUserRequest(
        String nickname,
        String email
) {
}
