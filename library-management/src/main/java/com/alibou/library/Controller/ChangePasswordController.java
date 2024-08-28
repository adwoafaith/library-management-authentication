package com.alibou.library.Controller;

import com.alibou.library.services.ChangePasswordService;
import com.alibou.library.Request.ChangePasswordRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;


@RestController
@RequestMapping("/api/v1/change-password")
@RequiredArgsConstructor


public class ChangePasswordController {
    private final ChangePasswordService changePasswordService;

        @PostMapping
        public ResponseEntity<?> changePassword(@Valid
                @RequestBody ChangePasswordRequest request,
                Principal principal
        ) {

            changePasswordService.changePassword(request,principal);
            return ResponseEntity.ok("Password changed successfully");
        }
}
