package com.demo.auth.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.auth.entity.app.Role;
import com.demo.auth.entity.app.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);

    List<User> findByRoles(Role role);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(String phoneNumber);
    
    Boolean existsByRefCode(String refCode);
}
