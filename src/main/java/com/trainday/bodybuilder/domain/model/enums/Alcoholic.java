package com.trainday.bodybuilder.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Alcoholic {
    DOESNT_DRINK("NAO_BEBE"),
    SOCIALLY("SOCIALMENTE"),
    FREQUENTELY("FREQUENTEMENTE"),
    SPORADICALLY("ESPORADICAMENTE");

    private final String state;
    Alcoholic(String state){
        this.state = state;
    }

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static Alcoholic formValue(String value){
        for (Alcoholic g : Alcoholic.values()) {
            if (g.state.equalsIgnoreCase(value)){
                return g;
            }
        }

        throw new IllegalArgumentException("Identity invalid: " + value);
    }
}
