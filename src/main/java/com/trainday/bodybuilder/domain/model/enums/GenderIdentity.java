package com.trainday.bodybuilder.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderIdentity {
    CISGENDER("Cisgênero"),
    TRANSGENDER("Transgênero");

    private final String state;

    GenderIdentity(String state) {
        this.state = state;
    }

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static GenderIdentity formValue(String value){
        for (GenderIdentity g : GenderIdentity.values()) {
            if (g.state.equalsIgnoreCase(value)){
                return g;
            }
        }

        throw new IllegalArgumentException("Identity invalid: " + value);
    }



}

