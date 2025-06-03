    package com.example.users.Repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import com.example.users.Entity.UsersEntity;

    import java.util.Optional;

    public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

        // 이메일로 사용자 조회
        Optional<UsersEntity> findByEmail(String email);

        // 이메일 중복 여부 확인
        boolean existsByEmail(String email);
    }