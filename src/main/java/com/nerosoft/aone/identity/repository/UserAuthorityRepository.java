package com.nerosoft.aone.identity.repository;

import com.nerosoft.aone.identity.domain.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, String> {
}
