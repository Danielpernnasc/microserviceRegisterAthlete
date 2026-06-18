package com.trainday.bodybuilder.domain.entiy.enums;

import com.trainday.bodybuilder.domain.model.enums.DiseaseType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiseaseTypeTest {
    @Test
    void shouldReturnDiseaseType_whenValidValue() {
        DiseaseType hiper = DiseaseType.formValue("HIPERTENSÃO");
        DiseaseType diab = DiseaseType.formValue("DIABETES");
        DiseaseType asma = DiseaseType.formValue("ASMA");
        DiseaseType obs = DiseaseType.formValue("OBESIDADE");
        DiseaseType alerg = DiseaseType.formValue("ALERGIA");
        DiseaseType outros = DiseaseType.formValue("OUTROS");

        assertEquals(DiseaseType.HYPERTENSION, hiper);
        assertEquals(DiseaseType.DIABETES, diab);
        assertEquals(DiseaseType.ASTHMA, asma);
        assertEquals(DiseaseType.OBESITY, obs);
        assertEquals(DiseaseType.ALLERGIC, alerg);
        assertEquals(DiseaseType.OTHERS, outros);

    }

    @Test
    void shouldThrowException_whenInvalidValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DiseaseType.formValue("invalid");
        });
        assertEquals("Identity invalid: invalid", exception.getMessage());
    }

    @Test
    void shouldReturn_WhenGetState(){
        String result = DiseaseType.ALLERGIC.getState();
        assertEquals("ALERGIA", result);
    }
}
