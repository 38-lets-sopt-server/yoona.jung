package org.sopt.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.global.common.dto.BaseResponse;
import org.sopt.user.dto.request.CreateUserRequest;
import org.sopt.user.dto.response.CreateUserResponse;
import org.sopt.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 생성", description = "새로운 유저를 생성합니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<CreateUserResponse>> createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("유저 생성 성공", response));
    }
}
