package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.dto.request.CreateUserRequest;
import org.sopt.user.dto.response.CreateUserResponse;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User(request.nickname(), request.email());
        userRepository.save(user);
        return new CreateUserResponse(user.getId());
    }
}
