package com.example.users.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    private int trustScore;

    private boolean isDormant;

    private boolean isAdmin;

    private LocalDateTime createdAt;

    // 🔽 유저는 여러 리프레시 토큰을 가질 수 있음
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RefreshTokenEntity> refreshTokens;
}