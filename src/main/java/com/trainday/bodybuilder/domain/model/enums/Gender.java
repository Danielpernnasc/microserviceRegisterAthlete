package com.trainday.bodybuilder.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    Male("Homem"),
    Female("Mulher"),
    NO_Binary("Não Binário");

    private final String state;

    Gender(String state) {
        this.state = state;
    }

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static Gender forValue(String value){
        for (Gender g : Gender.values()){
            if(g.state.equalsIgnoreCase(value)){
                return g;
            }
        }
        throw new IllegalArgumentException("Gender invalid: " + value);
    }

}
