package com.mimsoft.informesblackboard.application.data.constants;

public enum RolesPlatform {
    ADMIN(1),
    LECTOR(2),
    ;

    private final int value;

    RolesPlatform(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
