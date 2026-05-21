package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.request.LoginRequest;
import org.sopt.auth.dto.response.TokenResponse;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.auth.service.AuthService;
import org.sopt.global.common.dto.BaseResponse;
import org.sopt.global.exception.CustomException;
import org.sopt.user.dto.response.UserResponse;
import org.sopt.user.exception.UserErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @RequestBody LoginRequest request
            ) {
        TokenResponse tokens = authService.login(request.email(), request.password());
        return ResponseEntity.ok(BaseResponse.success("로그인 성공", tokens));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TokenResponse>> reissue(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new CustomException(AuthErrorCode.UNAUTHORIZED_HEADER);
        }

        String refreshToken = authorizationHeader.substring(7);
        TokenResponse tokens = authService.reissue(refreshToken);
        return ResponseEntity.ok(BaseResponse.success("토큰 재발급 성공", tokens));
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(
            @AuthenticationPrincipal Long userId
    ) {
        if (userId == null) {
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }
        UserResponse user = authService.getMemberById(userId);
        return ResponseEntity.ok(BaseResponse.success("검증에 성공했습니다.", user));
    }
}