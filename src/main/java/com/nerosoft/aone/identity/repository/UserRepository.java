package com.nerosoft.aone.identity.repository;

import com.nerosoft.aone.identity.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    @Query(value = "SELECT u.* FROM user AS u LEFT JOIN user_authority AS a ON u.id = a.user_id WHERE a.provider = :pvoder AND a.open_id = :openId", nativeQuery = true)
    Optional<User> findByOpenId(@Param("provider") String provider, @Param("openId") String openId);
}