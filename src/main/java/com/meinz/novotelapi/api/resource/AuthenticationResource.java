package com.meinz.novotelapi.api.resource;

import com.meinz.novotelapi.api.request.SignInRequest;
import com.meinz.novotelapi.api.request.SignUpRequest;
import com.meinz.novotelapi.api.response.DefaultResponse;
import com.meinz.novotelapi.api.response.JwtAuthenticationResponse;
import com.meinz.novotelapi.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
@AllArgsConstructor
public class AuthenticationResource {

   private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<DefaultResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}
