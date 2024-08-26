package com.alibou.library.Request;

import com.alibou.library.services.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

public class ChangePasswordRequest {


    @RestController
    @RequestMapping("/api/v1/users")
    @RequiredArgsConstructor


    public class ChangePassword {
        private final ChangePasswordService forgotPasswordService;

        @PatchMapping
        public ResponseEntity<?> changePassword(
                @RequestBody com.alibou.library.Controller.ChangePasswordRequest request,
                Principal ConnectedUser
        ) {

            Principal connectedUser = null;
            forgotPasswordService.changePassword(request, connectedUser);
            return ResponseEntity.ok().build();
        }
    }
}

