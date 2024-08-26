package com.alibou.library.Controller;

import com.alibou.library.services.ChangePasswordService;
import com.alibou.library.Request.ChangePasswordRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.security.Principal;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class ChangePasswordController {
    private final ChangePasswordService changePasswordService;

        @PatchMapping
        public ResponseEntity<?> changePassword(
                @RequestBody ChangePasswordRequest request,
                Principal ConnectedUser
        ) {

            Principal connectedUser = null;
            changePasswordService.changePassword(request,connectedUser);
            return ResponseEntity.ok().build();
        }
}
