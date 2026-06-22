package com.trainday.bodybuilder.application.service;

import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryRequest;
import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryUpdatePatchRequest;
import com.trainday.bodybuilder.api.DTO.request.RegisterRequest;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.HealthyHistory;
import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.model.enums.Alcoholic;
import com.trainday.bodybuilder.domain.model.enums.DiseaseType;
import com.trainday.bodybuilder.domain.repository.AthleteRepository;
import com.trainday.bodybuilder.domain.repository.HealtyHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mongodb.DuplicateKeyException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HealthyHistoryServiceTest {

    @Mock
    HealtyHistoryRepository hhRepository;

    @Mock
    AthleteRepository athleteRepository;

    @InjectMocks
    HealthyHistoryService hhService;


    @Test
    void shouldCreateHH(){
        RegisterRequest registerRequest = new RegisterRequest(
                "999.999.999-99",
                LocalDate.of(1980, 01, 01),
                "athlete@host.com",
                "******"


        );
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
                "Hipertensão por parte de pai e mãe"
        );
        Login login = new Login();
        login.setCpf(registerRequest.cpf());

        Athlete athlete = new Athlete();
        athlete.setCpf("999.999.999-99");

        when(athleteRepository.findByCpf(registerRequest.cpf()))
                .thenReturn(Optional.of(athlete));

        when(hhRepository.save(any(HealthyHistory.class)))
                .thenAnswer(invocation -> (HealthyHistory) invocation.getArgument(0));

        HealthyHistory create = hhService.createPacient(hhrequest, "999.999.999-99");

        assertNotNull(create);
        assertEquals(hhrequest.alcoholic(), create.getAlcoholic());
        assertEquals(hhrequest.physicallyActive(), create.getPhysicallyActive());
        assertEquals(hhrequest.diseases(), create.getDisease());
        assertEquals(hhrequest.medications(), create.getMedications());
        assertEquals(hhrequest.allergies(), create.isAllergies());
        assertEquals(hhrequest.whatAllergies(), create.getWhatAllergies());
        assertEquals(hhrequest.sugeries(), create.isSurgeries());
        assertEquals(hhrequest.whereSugeries(), create.getWheresurgeries());
        assertEquals(hhrequest.familyHistory(), create.getFamilyHistory());

    }

    @Test
    void shouldThrowConflictWhenHHAlreadyExist(){
        RegisterRequest registerRequest = new RegisterRequest(
                "999.999.999-99",
                LocalDate.of(1980, 01, 01),
                "athlete@host.com",
                "******"


        );
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
                "Hipertensão por parte de pai e mãe"
        );

        Athlete athlete = new Athlete();
        athlete.setCpf("999.999.999-99");



        when(athleteRepository.findByCpf(registerRequest.cpf()))
                .thenReturn(Optional.of(athlete));

       DuplicateKeyException duplicateKeyException = mock(DuplicateKeyException.class);

       when(hhRepository.save(any(HealthyHistory.class)))
               .thenThrow(duplicateKeyException);


        AthleteCpfAlreadyExistsException exception = assertThrows(
                AthleteCpfAlreadyExistsException.class,
                () -> hhService.createPacient(hhrequest, "999.999.999-99")
        );

        assertEquals(   "CPF already exists: 999.999.999-99", exception.getMessage());
        verify(hhRepository).save(any(HealthyHistory.class));
    }

    @Test
    void shouldThrowWhenAthleteNotFound() {
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
                "Hipertensão por parte de pai e mãe"
        );

        when(athleteRepository.findByCpf("999.999.999-99"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> hhService.createPacient(hhrequest, "999.999.999-99")
        );

        assertEquals("Athlete not found!", exception.getMessage());

        verify(hhRepository, never())
                .save(any(HealthyHistory.class));
    }


    @Test
    void shouldThrowConflictWhenHHtoUpdateDuplicate(){
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
                "Hipertensão por parte de pai e mãe"
        );

        HealthyHistory hh = new HealthyHistory();
        hh.setAthleteCpf("999.999.999-99");



        when(hhRepository.findByAthleteCpf(hh.getAthleteCpf()))
                .thenReturn(Optional.of(hh));


        DuplicateKeyException duplicateKeyException = mock(DuplicateKeyException.class);

        when(hhRepository.save(any(HealthyHistory.class)))
                .thenThrow(duplicateKeyException);


        HHbyCpfAlreadyExistsException exception = assertThrows(
                HHbyCpfAlreadyExistsException.class,
                () -> hhService.updateHH("999.999.999-99", hhrequest)
        );

        assertEquals(   "HH already exists: 999.999.999-99", exception.getMessage());
        verify(hhRepository).save(any(HealthyHistory.class));
    }

    @Test
    void shoudupdateHHByCPF() {
        HealthyHistoryUpdatePatchRequest hhrequest = new HealthyHistoryUpdatePatchRequest(
                false,
                Alcoholic.DOESNT_DRINK,
                true,
                List.of(DiseaseType.HYPERTENSION),
                List.of("Losartana"),
                true,
                List.of("Dipirona"),
                true,
                List.of("Cirugia no joelho esquerdo"),
                "Hipertensão por parte de pai e mãe"

        );

        HealthyHistory hhentity = new HealthyHistory();

        hhentity.setId("athlete@host.com");
        hhentity.setAthleteCpf("999.999.999-99");

        when(hhRepository.findByAthleteCpf("999.999.999-99"))
                .thenReturn(Optional.of(hhentity));


        when(hhRepository.findByAthleteCpf("999.999.999-99"))
                .thenReturn(Optional.of(hhentity));

        when(hhRepository.save(any(HealthyHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        HealthyHistory state = hhService.updateHH("999.999.999-99", hhrequest);
        assertNotNull(state);
        assertEquals("athlete@host.com", state.getId());
        assertEquals("999.999.999-99", state.getAthleteCpf());
        assertEquals(true, state.isSurgeries());
        assertEquals(List.of("Dipirona"), state.getWhatAllergies());
        assertEquals(true, state.isSurgeries());
        assertEquals(List.of("Cirugia no joelho esquerdo"), state.getWheresurgeries());
    }

    @Test
    void shouldThrowWhenHHnotFound(){
        HealthyHistory hhentity = new HealthyHistory(
                "athlete@host.com",
                "123.456.789-00",
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



        when(hhRepository.findByAthleteCpf("123.456.789-00"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> hhService.findByHHCPF(hhentity.getAthleteCpf())
        );

        assertEquals("Healthy History not found", exception.getMessage());


        DuplicateKeyException duplicateKeyException = mock(DuplicateKeyException.class);
        verify(hhRepository, never()).save(any(HealthyHistory.class));

    }



    @Test
    void shouldfindByHHCPF(){
        HealthyHistory hh = new HealthyHistory(
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
                "Hipertensão por parte de pai e mãe"
        );
        when(hhRepository.findByAthleteCpf("999.999.999-99"))
                .thenReturn(Optional.of(hh));

        HealthyHistory state = hhService.findByHHCPF("999.999.999-99");
        assertNotNull(state);
        assertEquals(hh.getId(), state.getId());
        assertEquals(hh.getAthleteCpf(), state.getAthleteCpf());
        assertEquals(hh.getSmoker(), state.getSmoker());
        assertEquals(hh.getAlcoholic(), state.getAlcoholic());
        assertEquals(hh.getPhysicallyActive(), state.getPhysicallyActive());
        assertEquals(hh.getDisease(), state.getDisease());
        assertEquals(hh.getMedications(), state.getMedications());
        assertEquals(hh.getDisease(), state.getDisease());
        assertEquals(hh.getMedications(), state.getMedications());
        assertEquals(hh.isAllergies(), state.isAllergies());
        assertEquals(hh.getWhatAllergies(), state.getWhatAllergies());
        assertEquals(hh.isSurgeries(), state.isSurgeries());
        assertEquals(hh.getWheresurgeries(), state.getWheresurgeries());
        assertEquals(hh.getFamilyHistory(), state.getFamilyHistory());

    }


    @Test
    void shouldpatchHH(){

        HealthyHistory existHH = new HealthyHistory();
        existHH.setId("9897654");
        existHH.setAthleteCpf("999.999.999-99");
        existHH.setSmoker(false);
        existHH.setAlcoholic(Alcoholic.SOCIALLY);
        existHH.setDisease(List.of(DiseaseType.HYPERTENSION));
        existHH.setMedications(List.of("Losartana"));
        existHH.setAllergies(true);
        existHH.setWhatAllergies(List.of("Dipirona"));
        existHH.setSurgeries(true);
        existHH.setWheresurgeries(List.of("Operação no Joelho esquerdo"));
        existHH.setFamilyHistory("Hipertensão por parte de Pai e Diabete por parte de mãe");
        HealthyHistory patchHH = new HealthyHistory();
        patchHH.setAllergies(true);
        patchHH.setWhatAllergies(List.of("Dipirona", "Amoxilina"));
        patchHH.setSurgeries(true);
        patchHH.setWheresurgeries(List.of("Joelho direito"));


        when(hhRepository.findByAthleteCpf("999.999.999-99"))
        .thenReturn(Optional.of(existHH));

        when(hhRepository.save(any(HealthyHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        HealthyHistory result = hhService.pathHH("999.999.999-99",
                new  HealthyHistoryUpdatePatchRequest(

                        false,
                        Alcoholic.DOESNT_DRINK,
                        true,
                        List.of(DiseaseType.HYPERTENSION, DiseaseType.OTHERS),
                        List.of("Losartana", "desvenlafaxina"),
                        true,
                        List.of("Dipirona", "Amoxilina"),
                        true,
                        List.of("Joelho direito"),
                        "Hipertensão por parte de pai e mãe"
                ));
        assertNotNull(result);
        assertEquals("9897654", result.getId());
        assertEquals("999.999.999-99", result.getAthleteCpf());
        assertEquals(List.of(DiseaseType.HYPERTENSION, DiseaseType.OTHERS), result.getDisease());
        assertEquals(List.of("Losartana", "desvenlafaxina"), result.getMedications());
        assertEquals(true, result.isAllergies());
        assertEquals(List.of("Dipirona", "Amoxilina"), result.getWhatAllergies());
        assertEquals(true, result.isSurgeries());
        assertEquals(List.of("Joelho direito"), result.getWheresurgeries());
        assertEquals("Hipertensão por parte de pai e mãe", result.getFamilyHistory());



    }




}
