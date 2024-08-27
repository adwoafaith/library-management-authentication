package com.alibou.library.Controller;

import com.alibou.library.Request.AuthenticationRequest;
import com.alibou.library.Request.RegistrationRequest;
import com.alibou.library.response.RegistrationResponse;
import com.alibou.library.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")

public class Authentication {
    private final AuthenticationService service;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)

    public ResponseEntity<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
       return service.register(request);

    }
    //login
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    )
    {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confrimAccount(
            @RequestParam String token
    ) throws MessagingException {
        service.activateAccount(token);
    }


}
