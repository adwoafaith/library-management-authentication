package com.alibou.library.handler;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
public enum BusinessErrorCodes {

    NO_CODE(0,NOT_IMPLEMENTED,"No Code"),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATH(301,BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED(302,FORBIDDEN,"User account is locked"),
    ACCOUNT_DISABLED(303,FORBIDDEN,"User account not verified"),
    BAD_CREDENTIALS(400,BAD_REQUEST, "Invalid email or password"),
    USER_NOT_FOUND(404, NOT_FOUND, "User not found"),
    EMAIL_ALREADY_EXISTS(409,CONFLICT,"Email already exists");

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code,HttpStatus httpStatus,String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
