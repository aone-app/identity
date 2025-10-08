package com.nerosoft.aone.identity.repository;

import com.nerosoft.aone.identity.domain.Token;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Token entity
 */
@Repository
public interface TokenRepository extends JpaRepository<@NonNull Token, @NonNull Long> {
    /** Find a token by its key **/
    Optional<Token> findByKey(String key);
}
