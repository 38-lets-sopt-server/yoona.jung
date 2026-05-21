package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.response.TokenResponse;
import org.sopt.auth.entity.RefreshToken;
import org.sopt.auth.repository.RefreshTokenRepository;
import org.sopt.user.domain.User;
import org.sopt.user.dto.response.UserResponse;
import org.sopt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.refresh-token-expires-in-seconds}")
    private long refreshTokenExpiresInSeconds;

    public UserResponse loginWithCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return UserResponse.from(user);
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        UserResponse member = loginWithCredentials(email, password);

        String accessToken = jwtService.generateAccessToken(member.id(), member.email());
        String refreshToken = jwtService.generateRefreshToken(member.id());

        refreshTokenRepository.deleteByMemberId(member.id());
        refreshTokenRepository.save(
                RefreshToken.of(member.id(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse reissue(String refreshToken) {
        RefreshToken stored = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Refresh Token입니다."));

        Long memberId = jwtService.verifyAndGetMemberId(refreshToken);
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        String newAccessToken = jwtService.generateAccessToken(memberId, user.getEmail());
        String newRefreshToken = jwtService.generateRefreshToken(memberId);
        stored.rotate(newRefreshToken, refreshTokenExpiresInSeconds);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    public UserResponse getMemberById(Long memberId) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return UserResponse.from(user);
    }
}
