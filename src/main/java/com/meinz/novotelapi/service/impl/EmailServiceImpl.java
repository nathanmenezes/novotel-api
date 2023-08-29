package com.meinz.novotelapi.service.impl;

import com.meinz.novotelapi.model.EmailToken;
import com.meinz.novotelapi.model.User;
import com.meinz.novotelapi.repository.EmailTokenRepository;
import com.meinz.novotelapi.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailTokenRepository emailTokenRepository;

    @Override
    public EmailToken findByUserAndUsed(User user) {
        return emailTokenRepository.findByUserIdAndUsed(user.getId(), false)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
    }

    @Override
    public EmailToken save(EmailToken emailToken) {
        return emailTokenRepository.save(emailToken);
    }

    @Override
    public EmailToken findByToken(UUID token) {
        return emailTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    }

    @Override
    public void useToken() {
        EmailToken emailToken = findByUserAndUsed(User.currentUser());
        emailToken.setUsed(true);
        emailTokenRepository.save(emailToken);
    }
}
