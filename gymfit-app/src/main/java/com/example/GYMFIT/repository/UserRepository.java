package com.example.GYMFIT.repository;

import com.example.GYMFIT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserEmail(String userEmail);   // 해당이메일 주소를 가진 사용자가 db에 존재하는지 여부 확인.

    Optional<User> findByUserEmail(String userEmail);
}
