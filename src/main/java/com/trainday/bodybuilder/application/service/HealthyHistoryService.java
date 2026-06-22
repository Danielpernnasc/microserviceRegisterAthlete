package com.trainday.bodybuilder.application.service;

import com.mongodb.DuplicateKeyException;

import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryRequest;
import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryUpdatePatchRequest;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.HealthyHistory;

import com.trainday.bodybuilder.domain.repository.AthleteRepository;
import com.trainday.bodybuilder.domain.repository.HealtyHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HealthyHistoryService {
    private static final String ATHLETE_NOT_FOUND = "Athlete not found!";
    private static final String HEALTY_HISTORY = "Healthy History not found";
    private static final String STRING = "String";
    private static final String ALLERGIE_INFORMED = "Allergie(s) not informed, Request it to be informed!";
    private static final String SUGERIO_INFORMED = "Surgery(ies) not informed, Request it to be informed!";

    private HealtyHistoryRepository hhrepository;
    private final AthleteRepository athleteRepository;

    public HealthyHistoryService(
            HealtyHistoryRepository hhrepository,
            AthleteRepository athleteRepository
    ) {
        this.hhrepository = hhrepository;
        this.athleteRepository = athleteRepository;
    }


    public HealthyHistory createPacient(
            HealthyHistoryRequest hhrequest, String cpf
    ) {

        Athlete athlete = athleteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException(ATHLETE_NOT_FOUND));


        HealthyHistory hh = new HealthyHistory();

        hh.setAthleteCpf(athlete.getCpf());

        hh.setSmoker(hhrequest.smoker());
        hh.setAlcoholic(hhrequest.alcoholic());
        hh.setPhysicallyActive(hhrequest.physicallyActive());
        hh.setDisease(hhrequest.diseases());
        hh.setMedications(hhrequest.medications());
        hh.setAllergies(hhrequest.allergies());
        hh.setSurgeries(hhrequest.sugeries());

        //Caso tenha alergia precisa especificar quais são e Se houve cirurgias onde foi feita
        validateAllergiesandSugeries(hhrequest.allergies(), hhrequest.whatAllergies(), hhrequest.sugeries(), hhrequest.whereSugeries());

        hh.setWhatAllergies(hhrequest.whatAllergies());
        hh.setWheresurgeries(hhrequest.whereSugeries());
        hh.setFamilyHistory(hhrequest.familyHistory());

        try {
            return hhrepository.save(hh);

        } catch (DuplicateKeyException e) {
            throw new AthleteCpfAlreadyExistsException(cpf);
        }


    }

    public HealthyHistory findByHHCPF(String athleteCpf) {
        return hhrepository.findByAthleteCpf(athleteCpf)
                .orElseThrow(() ->
                        new RuntimeException(HEALTY_HISTORY));
    }

    public HealthyHistory updateHH(String athleteCpf, HealthyHistoryUpdatePatchRequest updateHH)  {
        HealthyHistory existHH = hhrepository.findByAthleteCpf(athleteCpf)
                .orElseThrow(() -> new RuntimeException(HEALTY_HISTORY));

        Optional.ofNullable(updateHH.smoker())
                .ifPresent(existHH::setSmoker);

        Optional.ofNullable(updateHH.alcoholic())
                .ifPresent(existHH::setAlcoholic);

        Optional.ofNullable(updateHH.physicallyActive())
                .ifPresent(existHH::setPhysicallyActive);

        Optional.ofNullable(updateHH.diseases())
                .ifPresent(existHH::setDisease);

        Optional.ofNullable(updateHH.medications())
                .ifPresent(existHH::setMedications);

        Optional.ofNullable(updateHH.allergies())
                .ifPresent(existHH::setAllergies);

        Optional.ofNullable(updateHH.sugeries())
                .ifPresent(existHH::setSurgeries);
        //Caso tenha alergia precisa especificar quais são e Se houve cirurgias onde foi feita
        validateAllergiesandSugeries(updateHH.allergies(), updateHH.whatAllergies(), updateHH.sugeries(), updateHH.whereSugeries());
        Optional.ofNullable(updateHH.whatAllergies())
                .ifPresent(existHH::setWhatAllergies);

        Optional.ofNullable(updateHH.whereSugeries())
                .ifPresent(existHH::setWheresurgeries);

        Optional.ofNullable(updateHH.familyHistory())
                .ifPresent(existHH::setFamilyHistory);

                try {
                    return hhrepository.save(existHH);
                }catch (DuplicateKeyException e) {
                    throw new HHbyCpfAlreadyExistsException(existHH.getAthleteCpf());
                }

    }

    public HealthyHistory pathHH(String athleteCpf, HealthyHistoryUpdatePatchRequest req){
        HealthyHistory hh = hhrepository.findByAthleteCpf(athleteCpf)
                .orElseThrow(() -> new RuntimeException(HEALTY_HISTORY));

        if(req.smoker() != null){
            hh.setSmoker(req.smoker());
        }

        if(req.alcoholic() != null){
            hh.setAlcoholic(req.alcoholic());
        }

        if(req.physicallyActive() != null){
            hh.setPhysicallyActive(req.physicallyActive());
        }

      if(req.diseases() != null){
          hh.setDisease(req.diseases());
      }

        if(req.medications() != null) {
            hh.setMedications(req.medications());
        }

        if(req.allergies() != null){
            hh.setAllergies(req.allergies());
        }

        if(req.sugeries() != null){
            hh.setSurgeries(req.sugeries());
        }

        //Caso tenha alergia precisa especificar quais são e Se houve cirurgias onde foi feita
        validateAllergiesandSugeries(req.allergies(), req.whatAllergies(), req.sugeries(), req.whereSugeries());

        if(req.whatAllergies() != null){
            hh.setWhatAllergies(req.whatAllergies());
        }

        if(req.whereSugeries() != null){
            hh.setWheresurgeries(req.whereSugeries());
        }


        if(req.familyHistory() != null){
            hh.setFamilyHistory(req.familyHistory());
        }
        return hhrepository.save(hh);
    }

    void validateCpfAvailable(String cpf, String currentAthleteId) {
        if (cpf == null) {
            return;
        }
    }


    private void validateAllergiesandSugeries(
            Boolean allergies,
            List<String> whatAllergies,
            Boolean surgeries,
            List<String> whereSurgeries
    ) {

        if (
                Boolean.TRUE.equals(allergies)
                        && (
                        whatAllergies == null
                                || whatAllergies.isEmpty()
                                || whatAllergies.stream()
                                .allMatch(v ->
                                        v == null
                                                || v.isBlank()
                                                || STRING.equalsIgnoreCase(v))
                )
        ) {
            throw new IllegalArgumentException(
                    ALLERGIE_INFORMED
            );
        }

        if (
                Boolean.TRUE.equals(surgeries)
                        && (
                        whereSurgeries == null
                                || whereSurgeries.isEmpty()
                                || whereSurgeries.stream()
                                .allMatch(v ->
                                        v == null
                                                || v.isBlank()
                                                || STRING.equalsIgnoreCase(v))
                )
        ) {
            throw new IllegalArgumentException(
                    SUGERIO_INFORMED
            );
        }
    }
}


