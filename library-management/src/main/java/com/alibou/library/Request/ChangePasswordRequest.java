package com.alibou.library.Request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String confirmPassword;
}