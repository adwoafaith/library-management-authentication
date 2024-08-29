package com.alibou.library.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("token")
    private String token;

    private String id;


}