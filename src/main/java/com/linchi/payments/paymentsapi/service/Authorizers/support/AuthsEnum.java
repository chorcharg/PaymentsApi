package com.linchi.payments.paymentsapi.service.Authorizers.support;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthsEnum {

    BISMA ("bisma"),
    LINCHI ("linchi"),
    USER_ID("user_id"),
    NOT_FOUND("not_found");

    private final String name;

    AuthsEnum(String name){
        this.name = name;
    }


    public static AuthsEnum fromName(String name) {
        return Arrays.stream(AuthsEnum.values())
                .filter(
                        auth -> auth.name.equalsIgnoreCase(name.trim())
                )
                .findFirst()
                .orElse(NOT_FOUND);

    }


}
