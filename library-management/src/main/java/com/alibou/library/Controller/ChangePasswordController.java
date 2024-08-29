package com.alibou.library.Controller;

import com.alibou.library.response.ActivationResponse;
import com.alibou.library.response.LoginResponse;
import com.alibou.library.services.ChangePasswordService;
import com.alibou.library.Request.ChangePasswordRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor


public class ChangePasswordController {
    private final ChangePasswordService changePasswordService;

        @PutMapping(path = "/change-password/{id}")
        public ResponseEntity<ActivationResponse> changePassword(
                @Valid @RequestBody ChangePasswordRequest request,
                @PathVariable String id
        ) {

           return new ResponseEntity<>( changePasswordService.changePassword(request,id), HttpStatus.OK);

        }
}
