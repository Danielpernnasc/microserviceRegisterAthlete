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
    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static Role formValue(String value){
        for (Role g : Role.values()) {
            if (g.state.equalsIgnoreCase(value)){
                return g;
            }
        }

        throw new IllegalArgumentException("Identity invalid: " + value);
    }
}
