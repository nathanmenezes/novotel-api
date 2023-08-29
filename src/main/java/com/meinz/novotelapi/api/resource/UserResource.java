package com.meinz.novotelapi.api.resource;

import com.meinz.novotelapi.api.response.DefaultResponse;
import com.meinz.novotelapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;

    @GetMapping("/activate/{token}")
    public ResponseEntity<DefaultResponse> activate(@PathVariable UUID token) {
        return ResponseEntity.ok(userService.activate(token));
    }

    @GetMapping("/resend-activation-email")
    public ResponseEntity<DefaultResponse> resendActivationEmail() {
        return ResponseEntity.ok(userService.resendActivationEmail());
    }
}
