package com.trainday.bodybuilder.domain.entiy.enums;

import com.trainday.bodybuilder.domain.model.enums.Alcoholic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlcoholicTest {

    @Test
    void shouldReturnAlcoholic_whenValidValue(){
        Alcoholic naobebe = Alcoholic.formValue("NAO_BEBE");
        Alcoholic social = Alcoholic.formValue("SOCIALMENTE");
        Alcoholic frequente = Alcoholic.formValue("FREQUENTEMENTE");
        Alcoholic esporadico = Alcoholic.formValue("ESPORADICAMENTE");

        assertEquals(Alcoholic.DOESNT_DRINK, naobebe);
        assertEquals(Alcoholic.SOCIALLY, social);
        assertEquals(Alcoholic.FREQUENTELY, frequente);
        assertEquals(Alcoholic.SPORADICALLY, esporadico);

    }

    @Test
    void shouldThrowException_whenInvalidValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Alcoholic.formValue("invalid");
        });
        assertEquals("Identity invalid: invalid", exception.getMessage());
    }

    @Test
    void shouldReturn_WhenGetState(){
        String result = Alcoholic.DOESNT_DRINK.getState();
        assertEquals("NAO_BEBE", result);
    }
}
