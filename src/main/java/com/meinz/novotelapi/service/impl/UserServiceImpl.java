package com.meinz.novotelapi.service.impl;

import com.meinz.novotelapi.api.response.DefaultResponse;
import com.meinz.novotelapi.model.EmailToken;
import com.meinz.novotelapi.model.User;
import com.meinz.novotelapi.repository.EmailTokenRepository;
import com.meinz.novotelapi.repository.UserRepository;
import com.meinz.novotelapi.service.EmailService;
import com.meinz.novotelapi.service.UserService;
import com.meinz.novotelapi.util.EmailUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final EmailTokenRepository emailTokenRepository;

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public DefaultResponse activate(UUID token) {
        EmailToken emailToken = emailService.findByToken(token);
        if(emailToken.getUser().getIsActive()) {
            return DefaultResponse.builder().message("User already activated").build();
        }
        if(!Objects.equals(emailToken.getUser().getId(), User.currentUser().getId())){
            return DefaultResponse.builder().message("Invalid token").build();
        }
        if(emailToken.getUsed()){
            return DefaultResponse.builder().message("Token already used").build();
        }
        emailToken.getUser().setIsActive(true);
        userRepository.save(emailToken.getUser());
        emailToken.setUsed(true);
        return DefaultResponse.builder().message("User activated").build();
    }

    @Override
    public DefaultResponse resendActivationEmail() {
        emailService.useToken();
        EmailUtils.sendEmail(User.currentUser(), emailService.save(EmailToken.builder().token(UUID.randomUUID()).user(User.currentUser()).build()));
        return DefaultResponse.builder().message("Activation email sent").build();
    }

    @Override
    public User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
