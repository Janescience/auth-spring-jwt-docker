package com.demo.auth.repository;

import org.springframework.stereotype.Repository;

import com.demo.auth.entity.app.Role;
import com.demo.auth.entity.enums.ERole;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
    Optional<Role> findByName(ERole name);
}
