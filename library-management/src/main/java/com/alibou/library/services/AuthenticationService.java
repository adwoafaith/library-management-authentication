package com.alibou.library.services;


import com.alibou.library.Repository.RoleRepository;
import com.alibou.library.Repository.TokenRepository;
import com.alibou.library.Repository.UserRepository;
import com.alibou.library.Request.loginRequest;
import com.alibou.library.Request.RegistrationRequest;
import com.alibou.library.handler.BusinessErrorCodes;
import com.alibou.library.handler.BusinessException;
import com.alibou.library.handler.TokenNotFoundException;
import com.alibou.library.modal.Token;
import com.alibou.library.modal.User;
import com.alibou.library.response.ActivationResponse;
import com.alibou.library.response.LoginResponse;
import com.alibou.library.response.RegistrationResponse;
import com.alibou.library.security.JwtService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private  final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Value("${confirmationUrl}")
    private String activationUrl;


    public ResponseEntity<RegistrationResponse> register(@Valid RegistrationRequest request) throws MessagingException {

        //check if the email already exists
        if(userRepository.existsByEmail(request.getEmail().trim())){
            throw new BusinessException(BusinessErrorCodes.EMAIL_ALREADY_EXISTS);
        }

        //To register a user we need to assign it a role by default and by default they are users
        // we fetch the role by name and assign it to the user
        //create a user object and save it
        //send a validation email to the user
        var userRole = roleRepository.findByRole("USER")

                //todo - better exception handling
                .orElseThrow(() ->  new BusinessException(BusinessErrorCodes.USER_ROLE_NOT_FOUND));
        var user = User.builder()
                .firstname(request.getFirstName().trim())
                .lastname(request.getLastName().trim())
                .email(request.getEmail().trim())
                .password(passwordEncoder.encode(request.getPassword().trim()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);

        sendValidationEmail(user);
        return ResponseEntity.ok(RegistrationResponse.builder()
                .message("User registered Successfully.Please check your mail for verification")
                        .statusCode(200)
                .build());

    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        //send email

        emailService.sendEmail(
                user.getEmail(),
                "Account activation",
                user.getFirstname(),
                EmailTempelate.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                null
        );

    }

    private String generateAndSaveActivationToken(User user) {
        //generate a token
        String  generateToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expires(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode(int length) {
        //we are generating an activation code composed of 6 digits
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());  //0..9
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public ResponseEntity<LoginResponse> login(@Valid loginRequest request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail().trim(),
                            request.getPassword().trim()
                    )
            );
            var claims = new HashMap<String, Object>();
            var user = ((User) auth.getPrincipal());
            claims.put("fullName", user.fullname());
            var jwtToken = jwtService.generateToken(claims, user);
            return ResponseEntity.ok(LoginResponse.builder()
                    .id(String.valueOf(user.getId()))
                    .message("Login successful!")
                    .statusCode(200)
                    .token(jwtToken)
                    .userId(String.valueOf(user.getId()))
                    .build());
        } catch (BadCredentialsException e) {

            e.printStackTrace();
            throw new BusinessException(BusinessErrorCodes.USER_NOT_FOUND);
        }
    }

    public ResponseEntity<ActivationResponse> activateAccount(String token) throws MessagingException {
        try {
            Token savedToken = tokenRepository.findByToken(token)
                    .orElseThrow(() -> new TokenNotFoundException("Token does not exist"));

            if (LocalDateTime.now().isAfter(savedToken.getExpires())) {
                User user = savedToken.getUser();
                sendValidationEmail(user);
                throw new TokenNotFoundException.TokenExpiredException("Token has expired. A new token has been sent to your email");
            }

            var user = userRepository.findById(String.valueOf(savedToken.getUser().getId()))
                    .orElseThrow(() -> new TokenNotFoundException.UserNotFoundException("User does not exist"));

            user.setEnabled(true);
            userRepository.save(user);
            savedToken.setValidatedAt(LocalDateTime.now());
            tokenRepository.save(savedToken);

            return ResponseEntity.ok(ActivationResponse.builder()
                    .message("Account activated successfully")
                    .statusCode(HttpStatus.OK.value())
                    .build());
        } catch (TokenNotFoundException | TokenNotFoundException.TokenExpiredException | TokenNotFoundException.UserNotFoundException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ActivationResponse.builder()
                            .message(e.getMessage())
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
    }

        }




