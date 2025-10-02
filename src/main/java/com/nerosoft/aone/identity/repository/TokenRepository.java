package com.nerosoft.aone.identity.repository;

import com.nerosoft.aone.identity.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    
}
