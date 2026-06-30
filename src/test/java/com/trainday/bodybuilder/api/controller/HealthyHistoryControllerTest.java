package com.trainday.bodybuilder.api.controller;

import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryRequest;
import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryUpdatePatchRequest;
import com.trainday.bodybuilder.application.service.HealthyHistoryService;
import com.trainday.bodybuilder.domain.model.HealthyHistory;
import com.trainday.bodybuilder.domain.model.enums.Alcoholic;
import com.trainday.bodybuilder.domain.model.enums.DiseaseType;
import com.trainday.bodybuilder.domain.repository.HealtyHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HealthyHistoryControllerTest {
    @Mock
    HealthyHistoryService hhService;

    @Mock
    HealtyHistoryRepository hhRepository;

    @InjectMocks
    HealthyHistoryController hhController;

    @Test
    void shoudSaveHH(){
        HealthyHistoryRequest hhrequest = new HealthyHistoryRequest(
                false,
                Alcoholic.DOESNT_DRINK,
                true,
                List.of(DiseaseType.HYPERTENSION),
                List.of("Losartana"),
                false,
                null,
                false,
                null,
                "Hipertensão por parte de Pai e Mãe"
        );

        HealthyHistory hh = new HealthyHistory();
        hh.setId("athlete@host.com");
        hh.setAthleteCpf("999.999.999-99");
        hh.setSmoker(false);
        hh.setAlcoholic(Alcoholic.DOESNT_DRINK);
        hh.setDisease(List.of(DiseaseType.HYPERTENSION));
        hh.setMedications(List.of("Losartana"));
        hh.setAllergies(false);
        hh.setWhatAllergies(null);
        hh.setSmoker(false);
        hh.setWheresurgeries(null);
        hh.setFamilyHistory("Hipertensão por parte de Pai e Mãe");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticate");

        when(hhService.createPacient(hhrequest, "authenticate"))
                .thenReturn(hh);


        ResponseEntity<HealthyHistory> response =
                hhController.save(hhrequest, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        HealthyHistory created = hhController.save(hhrequest, authentication).getBody();

        assertNotNull(created);
        assertEquals(hh.getId(), created.getId());
        assertEquals(hh.getAthleteCpf(), created.getAthleteCpf());
        assertEquals(hh.getSmoker(), created.getSmoker());
        assertEquals(hh.getAlcoholic(), created.getAlcoholic());
        assertEquals(hh.getDisease(), created.getDisease());
        assertEquals(hh.getMedications(), created.getMedications());
        assertEquals(hh.isAllergies(), created.isAllergies());
        assertEquals(hh.isSurgeries(), created.isSurgeries());
        assertEquals(hh.getWheresurgeries(), created.getWhatAllergies());
        assertEquals(hh.getWheresurgeries(), created.getWheresurgeries());
        assertEquals(hh.getFamilyHistory(), created.getFamilyHistory());


    }

    @Test
    void shouldFindByCPF(){

        HealthyHistory hhentity = new HealthyHistory(
                "athlete@host.com",
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
                "Hipertensão por parte de Pai e Mãe"
        );


        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticate");

        when(hhService.findByHHCPF( "authenticate"))
                .thenReturn(hhentity);


        ResponseEntity<HealthyHistory> response =
                hhController.searchCpf(authentication);

        assertNotNull(response);

        HealthyHistory body = response.getBody();


        assertEquals("999.999.999-99", body.getAthleteCpf());
        assertEquals(false, body.getSmoker());
        assertEquals(Alcoholic.DOESNT_DRINK, body.getAlcoholic());
        assertEquals(true, body.getPhysicallyActive());
        assertEquals(List.of(DiseaseType.HYPERTENSION), body.getDisease());
        assertEquals(List.of("Losartana"), body.getMedications());
        assertEquals(false,  body.isAllergies());
        assertEquals(null, body.getWhatAllergies());
        assertEquals(false, body.isSurgeries());
        assertEquals( null, body.getWheresurgeries());
        assertEquals("Hipertensão por parte de Pai e Mãe", body.getFamilyHistory());


    }

    @Test
    void shouldUptadeHH(){

        HealthyHistoryUpdatePatchRequest hhrequest = new HealthyHistoryUpdatePatchRequest(
                false,
                Alcoholic.DOESNT_DRINK,
                true,
                List.of(DiseaseType.HYPERTENSION),
                List.of("Losartana"),
                false,
                null,
                false,
                null,
                "Hipertensão por parte de Pai e Mãe"
        );

        HealthyHistory hh = new HealthyHistory();
        hh.setId("athlete@host.com");
        hh.setAthleteCpf("999.999.999-99");
        hh.setSmoker(false);
        hh.setPhysicallyActive(true);
        hh.setAlcoholic(Alcoholic.SOCIALLY);
        hh.setDisease(List.of(DiseaseType.HYPERTENSION));
        hh.setMedications(List.of("Losartana"));
        hh.setAllergies(true);
        hh.setWhatAllergies(List.of("Dipirona"));
        hh.setSurgeries(true);
        hh.setWheresurgeries(List.of("Operação no joelho esquerdo"));
        hh.setFamilyHistory("Hipertensão por parte de Pai e Mãe");
        String cpf = "999.999.999-99";

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(cpf);

        when(hhService.updateHH(cpf, hhrequest))
                .thenReturn(hh);

        HealthyHistory update = hhController.updateHH(authentication, hhrequest).getBody();
        assertEquals(false, hh.getSmoker());
        assertEquals(Alcoholic.SOCIALLY, hh.getAlcoholic());
        assertEquals(true,  hh.getPhysicallyActive());
        assertEquals(List.of(DiseaseType.HYPERTENSION), hh.getDisease());
        assertEquals(List.of("Losartana"), hh.getMedications());
        assertEquals(true,  hh.isAllergies());
        assertEquals(List.of("Dipirona"), hh.getWhatAllergies());
        assertEquals(true, hh.isSurgeries());
        assertEquals( List.of("Operação no joelho esquerdo"), hh.getWheresurgeries());
        assertEquals("Hipertensão por parte de Pai e Mãe", hh.getFamilyHistory());

    }

    @Test
    void shouldPatchHH(){

        HealthyHistoryUpdatePatchRequest hhrequest = new HealthyHistoryUpdatePatchRequest(
                false,
                Alcoholic.DOESNT_DRINK,
                true,
                List.of(DiseaseType.HYPERTENSION),
                List.of("Losartana"),
                false,
                null,
                false,
                null,
                "Hipertensão por parte de Pai e Mãe"
        );

        HealthyHistory hh = new HealthyHistory();
        hh.setId("athlete@host.com");
        hh.setAthleteCpf("999.999.999-99");
        hh.setSmoker(true);
        String cpf = "999.999.999-99";

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(cpf);

        when(hhService.pathHH(cpf, hhrequest))
                .thenReturn(hh);
        HealthyHistory result = hhController.patchHH(authentication, hhrequest);

        assertEquals(true, hh.getSmoker());


    }
}