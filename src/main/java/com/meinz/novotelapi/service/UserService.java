package com.meinz.novotelapi.service;

import com.meinz.novotelapi.api.response.DefaultResponse;
import com.meinz.novotelapi.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService {
    UserDetailsService userDetailsService();

    DefaultResponse activate(UUID token);

    DefaultResponse resendActivationEmail();

    User findByEmail(String userEmail);
}
