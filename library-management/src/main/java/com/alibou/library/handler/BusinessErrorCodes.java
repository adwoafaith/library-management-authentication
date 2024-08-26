package com.alibou.library.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCodes {

    NO_CODE(0,NOT_IMPLEMENTED,"No Code"),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATH(301,BAD_REQUEST, "The new password does not math"),
    ACCOUNT_LOCKED(302,FORBIDDEN,"User account is locked"),
    ACCOUNT_DISABLED(303,FORBIDDEN,"User account is disabled"),
    BAD_CREDENTIALS(400,BAD_REQUEST, "Invalid email or password"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code,HttpStatus httpStatus,String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
