package com.trainday.bodybuilder.api.DTO.request;

import com.trainday.bodybuilder.domain.model.enums.Alcoholic;
import com.trainday.bodybuilder.domain.model.enums.DiseaseType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealtyHistoryRequestTest {

    @Test
    void shouldHealtyHistoryRequest(){
        HealthyHistoryRequest hhrequest = new HealthyHistoryRequest(
                "999.999.999-99",
                false,
                Alcoholic.DOESNT_DRINK,
                true,
                List.of(DiseaseType.HYPERTENSION),
                 List.of("Losartana"),
                false,
                null,
                false,
                null,
                "Hipertensão por parte de pai e mãe"

        );
        assertEquals("999.999.999-99", hhrequest.athleteCpf());
        assertEquals(false, hhrequest.smoker());
        assertEquals(Alcoholic.DOESNT_DRINK, hhrequest.alcoholic());
        assertEquals(true, hhrequest.physicallyActive());
        assertEquals(List.of(DiseaseType.HYPERTENSION), hhrequest.diseases());
        assertEquals(List.of("Losartana"), hhrequest.medications());
        assertEquals(false, hhrequest.allergies());
        assertEquals(null, hhrequest.whatAllergies());
        assertEquals(false, hhrequest.sugeries());
        assertEquals(null, hhrequest.whereSugeries());
        assertEquals("Hipertensão por parte de pai e mãe", hhrequest.familyHistory());


    }
}
