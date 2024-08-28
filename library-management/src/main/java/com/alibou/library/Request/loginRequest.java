package com.alibou.library.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;




@Data
public class loginRequest {

    @Email(message = "Email should be in a proper format")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(min = 8, message = "Password should be 8 characters long")
    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
