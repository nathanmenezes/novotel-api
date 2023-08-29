package com.meinz.novotelapi.repository;

import com.meinz.novotelapi.model.EmailToken;
import com.meinz.novotelapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByToken(UUID token);

    Optional<EmailToken> findByUserIdAndUsed(Long user_id, Boolean used);
}
