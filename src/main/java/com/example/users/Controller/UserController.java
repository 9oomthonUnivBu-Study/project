package com.example.users.Controller;

import com.example.users.Dto.*;
import com.example.users.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody UserSignupRequestDto requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("회원가입이 완료되었습니다.", LocalDateTime.now(), null));
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@RequestBody UserLoginRequestDto requestDto) {
        ApiResponse<TokenResponseDto> response = authService.login(requestDto);
        return ResponseEntity.ok(response);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestParam Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok(new ApiResponse<>("로그아웃 되었습니다.", LocalDateTime.now(), null));
    }
}