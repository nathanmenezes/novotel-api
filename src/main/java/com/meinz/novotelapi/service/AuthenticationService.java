package com.meinz.novotelapi.service;

import com.meinz.novotelapi.api.request.SignInRequest;
import com.meinz.novotelapi.api.request.SignUpRequest;
import com.meinz.novotelapi.api.response.DefaultResponse;
import com.meinz.novotelapi.api.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    DefaultResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
