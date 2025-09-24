package com.nerosoft.aone.identity.repository;

import com.nerosoft.aone.identity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);
}