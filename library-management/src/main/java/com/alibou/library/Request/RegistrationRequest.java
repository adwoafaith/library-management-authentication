package com.alibou.library.Request;


import com.alibou.library.handler.ValidPassword;
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

    @ValidPassword(message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long")
    @NotEmpty(message = "Password is required")
    private String password;
}
