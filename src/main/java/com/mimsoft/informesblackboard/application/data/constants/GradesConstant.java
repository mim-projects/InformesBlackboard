package com.mimsoft.informesblackboard.application.data.constants;

public enum GradesConstant {
    Licenciatura(1),
    Posgrado(2),
    ;

    private final int value;

    GradesConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
