package com.mimsoft.informesblackboard.application.data.constants;

public enum RolesConstant {
    Estudiantes(1),
    Docentes(2),
    ;

    private final int value;

    RolesConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
