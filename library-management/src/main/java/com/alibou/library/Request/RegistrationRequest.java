package com.alibou.library.Request;


import com.alibou.library.CustomerAnnotations.CustomEmail;
import com.alibou.library.CustomerAnnotations.ValidatePassword;
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
    @CustomEmail(message = "Email should be in a proper format with .com or .co domain")
    private String email;

    @ValidatePassword(message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long")
    @NotEmpty(message = "Password is required")
    private String password;
}
