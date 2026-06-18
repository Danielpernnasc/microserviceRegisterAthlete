package com.trainday.bodybuilder.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiseaseType {
    HYPERTENSION("HIPERTENSÃO"),
    DIABETES("DIABETES"),
    ASTHMA("ASMA"),
    CARDIOPATHY("CARDIOPATIA"),
    OBESITY("OBESIDADE"),
    ALLERGIC("ALERGIA"),
    OTHERS("OUTROS");

    private final String  state;
    DiseaseType(String state) {this.state = state;}

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static DiseaseType formValue(String value){
        for (DiseaseType g :DiseaseType.values()) {
            if (g.state.equalsIgnoreCase(value)){
                return g;
            }
        }

        throw new IllegalArgumentException("Identity invalid: " + value);
    }
}
