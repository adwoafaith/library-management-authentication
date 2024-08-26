package com.alibou.library.services;

import lombok.Data;
import lombok.Getter;

@Getter
public enum EmailTempelate {

    ACTIVATE_ACCOUNT("activate_account");

    private final String name;

    EmailTempelate(String name) {
        this.name = name;
    }
}
