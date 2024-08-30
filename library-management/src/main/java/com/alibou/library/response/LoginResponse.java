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

<<<<<<< HEAD
    @JsonProperty("userId")
    private String userId;
=======
    private String id;
>>>>>>> 1adf21fd66cf9839e5f5bc4bcb53c388060ba5df


}