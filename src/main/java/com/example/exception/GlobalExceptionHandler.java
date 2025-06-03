package com.example.exception;

import com.example.users.Dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException → 잘못된 요청 (입력 누락, 형식 오류 등)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                new ApiResponse<>(ex.getMessage(), LocalDateTime.now(), null)
        );
    }

    // EntityNotFoundException → 주로 DB에서 사용자 찾을 수 없을 때
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(ex.getMessage(), LocalDateTime.now(), null)
        );
    }

    // 기타 예외 처리 (서버 에러)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>("서버 내부 오류가 발생했습니다.", LocalDateTime.now(), null)
        );
    }
}