package com.alibou.library.Request;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    private String firstName;

    @NotEmpty(message = "lastname is mandatory")
    private String lastName;

    @Email(message = "Email should be in a proper format")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(min = 8, message = "Password should be 8 characters long")
    @NotEmpty(message = "Password is required")
    private String password;
}
