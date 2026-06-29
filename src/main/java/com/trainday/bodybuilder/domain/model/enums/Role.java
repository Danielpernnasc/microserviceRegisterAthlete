package com.trainday.bodybuilder.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ATHLETE("ATLETA"),
    PERSONAL_TRAINER("EDUCADOR_FISICO"),
    DOCTOR("MÉDICO"),
    NUTRITIONIST("NUTRICIONISTA"),
    LAB_TECHNIC("TEC_LAB"),
    ADMIN("ADMIN");

    private final String state;

    Role(String state) {
        this.state = state;
    }

    public static void values(String invalid) {
    }

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static Role fromValue(String value) {

        for (Role role : Role.values()) {

            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }

            if (role.getState().equalsIgnoreCase(value)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Role inválida: " + value);
    }
}
