package com.thanhdatpb.javamentorhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhdatpb.javamentorhub.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Tìm Role theo tên (roleName),
     * dùng cho logic phân quyền khi đăng nhập.
     */
    Optional<Role> findByName(String roleName);
}
