package com.meinz.novotelapi.service.impl;

import com.meinz.novotelapi.api.request.SignInRequest;
import com.meinz.novotelapi.api.request.SignUpRequest;
import com.meinz.novotelapi.api.response.DefaultResponse;
import com.meinz.novotelapi.api.response.JwtAuthenticationResponse;
import com.meinz.novotelapi.model.EmailToken;
import com.meinz.novotelapi.model.User;
import com.meinz.novotelapi.model.enums.Role;
import com.meinz.novotelapi.repository.UserRepository;
import com.meinz.novotelapi.service.AuthenticationService;
import com.meinz.novotelapi.service.JwtService;
import com.meinz.novotelapi.util.EmailUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailService;

    @Override
    public DefaultResponse signUp(SignUpRequest request) {
        var user = User.builder().name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .isActive(false)
                .isTwoFactorEnabled(false)
                .build();
        if(userRepository.existsByEmail(user.getEmail())){
            return DefaultResponse.builder().message("Email already exists").build();
        }
        userRepository.save(user);
        EmailUtils.sendEmail(user, emailService.save(EmailToken.builder().token(UUID.randomUUID()).user(user).used(false).build()));
        var jwt = jwtService.generateToken(user);
        return DefaultResponse.builder().message("User created, active your account in email.").data(JwtAuthenticationResponse.builder().token(jwt).build()).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
