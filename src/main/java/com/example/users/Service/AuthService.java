package com.example.users.Service;

import com.example.users.Dto.ApiResponse;
import com.example.users.Dto.TokenResponseDto;
import com.example.users.Dto.UserLoginRequestDto;
import com.example.users.Dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.users.Entity.UsersEntity;
import com.example.users.Entity.RefreshTokenEntity;
import com.example.users.Repository.UsersRepository;
import com.example.users.Repository.RefreshTokenRepository;
import com.example.JWT.JwtUtil;
import com.example.users.Dto.ApiResponse;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(UserSignupRequestDto requestDto) {
        // 1. 이메일 중복 확인
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 3. 엔티티 생성 및 저장
        UsersEntity user = UsersEntity.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .username(requestDto.getUsername())
                .trustScore(0)
                .isAdmin(false)
                .isDormant(false)
                .createdAt(LocalDateTime.now())
                .build();

        usersRepository.save(user);
    }

    public ApiResponse<TokenResponseDto> login(UserLoginRequestDto requestDto) {
        UsersEntity user = usersRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입된 유저가 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user.getId());
        String refreshToken = jwtUtil.createRefreshToken(user.getId());

        RefreshTokenEntity tokenEntity = RefreshTokenEntity.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(jwtUtil.getRefreshTokenExpiryDate())
                .build();

        refreshTokenRepository.save(tokenEntity);

        return new ApiResponse<>(
                "로그인 성공",
                LocalDateTime.now(),
                new TokenResponseDto(accessToken, refreshToken)
        );
    }

    @Transactional
    public void logout(Long userId) {
        // 사용자 id로 저장된 refresh token 삭제
        refreshTokenRepository.deleteAllByUser_Id(userId);
    }

    public ApiResponse<TokenResponseDto> refresh(String refreshToken) {
        // 1. DB에 해당 리프레시 토큰이 존재하는지 확인
        RefreshTokenEntity tokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다."));

        // 2. 토큰 유효성 확인
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new IllegalArgumentException("만료된 리프레시 토큰입니다.");
        }

        // 3. 사용자 ID 추출
        Long userId = jwtUtil.getUserIdFromToken(refreshToken);

        // 4. 새로운 액세스 토큰 생성
        String newAccessToken = jwtUtil.createAccessToken(userId);

        // 5. 응답 포맷으로 감싸서 반환
        TokenResponseDto tokenResponseDto = new TokenResponseDto(newAccessToken, null);
        return new ApiResponse<>("토큰 재발급 성공", LocalDateTime.now(), tokenResponseDto);
    }
}