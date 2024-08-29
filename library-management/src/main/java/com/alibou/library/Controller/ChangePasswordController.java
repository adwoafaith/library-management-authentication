package com.alibou.library.Controller;

import com.alibou.library.services.ChangePasswordService;
import com.alibou.library.Request.ChangePasswordRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;


@RestController
@RequestMapping("/api/v1/change-password/{userId}")
@RequiredArgsConstructor


public class ChangePasswordController {
    private final ChangePasswordService changePasswordService;

        @PutMapping
        public ResponseEntity<?> changePassword(@Valid
                @RequestBody ChangePasswordRequest request,@PathVariable String userId
        ) {

            changePasswordService.changePassword(request,userId);
            return ResponseEntity.ok("Password changed successfully");
        }
}
