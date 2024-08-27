package com.alibou.library.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
@Builder
public class RegistrationResponse {
    private String message;
    private int statusCode;
}
