package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.CustomException;
import org.sopt.user.domain.User;
import org.sopt.user.dto.request.CreateUserRequest;
import org.sopt.user.dto.response.CreateUserResponse;
import org.sopt.user.exception.UserErrorCode;
import org.sopt.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail((request.email()))) {
            throw new CustomException(UserErrorCode.DUPLICATED_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.nickname(), request.email(), encodedPassword);
        userRepository.save(user);
        return new CreateUserResponse(user.getId());
    }
}
