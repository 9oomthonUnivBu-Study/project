package com.example.users.Controller;

import com.example.JWT.JwtUtil;
import com.example.users.Dto.ApiResponse;
import com.example.users.Dto.TokenRefreshRequestDto;
import com.example.users.Dto.TokenResponseDto;
import com.example.users.Repository.RefreshTokenRepository;
import com.example.users.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponseDto>> refresh(@RequestBody TokenRefreshRequestDto requestDto) {
        return ResponseEntity.ok(authService.refresh(requestDto.getRefreshToken()));
    }
}