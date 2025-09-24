package com.nerosoft.aone.identity.repository;

import com.nerosoft.aone.identity.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    
}
