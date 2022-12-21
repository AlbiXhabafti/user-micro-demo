package com.example.userdemo.repository;

import com.example.userdemo.enums.RoleEnum;
import com.example.userdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleEnum(RoleEnum name);
}
