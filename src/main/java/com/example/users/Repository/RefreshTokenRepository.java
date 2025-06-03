package com.example.users.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.users.Entity.RefreshTokenEntity;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    // 토큰 값으로 조회
    Optional<RefreshTokenEntity> findByToken(String token);

    // 특정 유저의 모든 리프레시 토큰 조회
    List<RefreshTokenEntity> findByUser_Id(Long userId);

    // 특정 유저의 가장 최근 토큰 조회
    Optional<RefreshTokenEntity> findTopByUser_IdOrderByExpiryDateDesc(Long userId);

    // 🔽 추가: 삭제 메서드 선언
    void deleteAllByUser_Id(Long userId);
}