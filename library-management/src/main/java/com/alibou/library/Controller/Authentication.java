package com.alibou.library.Controller;

import com.alibou.library.Request.loginRequest;
import com.alibou.library.Request.RegistrationRequest;
import com.alibou.library.response.ActivationResponse;
import com.alibou.library.response.LoginResponse;
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
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid loginRequest request
    )
    {
        return service.login(request);
    }

    @GetMapping("/activate-account")
    public ResponseEntity<ActivationResponse> confrimAccount(
            @RequestParam String token
    ) throws MessagingException {
         return service.activateAccount(token);
    }


}
