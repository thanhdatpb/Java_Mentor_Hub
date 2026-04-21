package com.thanhdatpb.javamentorhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhdatpb.javamentorhub.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Tìm user theo email (duy nhất), dùng khi đăng nhập và kiểm tra trùng email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Tìm user theo username (duy nhất), dùng cho API quản lý người dùng.
     */
    Optional<User> findByUsername(String username);
}
