package com.alibou.library.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ActivationResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("statusCode")
    private int statusCode;
}
