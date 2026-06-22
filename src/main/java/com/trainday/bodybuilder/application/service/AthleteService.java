package com.trainday.bodybuilder.application.service;


import java.util.Optional;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;
import com.trainday.bodybuilder.api.DTO.request.AthleteRequest;
import com.trainday.bodybuilder.api.DTO.response.AthleteResponse;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.repository.AthleteRepository;
import com.trainday.bodybuilder.domain.repository.LoginRepository;

@Service
public class AthleteService {

    private final AthleteRepository athleterepository;
    private final LoginRepository loginRepository;
    private static final String ATHLETE_NOT_FOUND_ID = "Athlete not found with id! ";
    private static final String ATHLETE_NOT_FOUND = "Athlete not found! ";
    private static final String NOT_FOUND_ID_DELETE = "Not found the id for Delete";



    public AthleteService(
        AthleteRepository athleterepository,
        LoginRepository loginRepository
     
    ){
        this.athleterepository = athleterepository;
        this.loginRepository = loginRepository;
    
    }

    public Athlete createAthlete(AthleteRequest reqAthlete, String athleteId){

        System.out.println("ATHLETE ID = " + athleteId);

        Login user = loginRepository.findByCpf(athleteId)
                .orElseThrow(() -> new RuntimeException(ATHLETE_NOT_FOUND));


        if (athleterepository.findByCpf(user.getCpf()).isPresent()) {
            throw new AthleteCpfAlreadyExistsException(user.getCpf());
        }

        Athlete athlete = new Athlete();
        athlete.setId(user.getCpf());
        athlete.setCpf(user.getCpf());
        athlete.setName(reqAthlete.name());
        athlete.setSocialname(reqAthlete.socialName());
        athlete.setEmail(user.getEmail());
        athlete.setBorn(user.getBorn());
        athlete.setGender(reqAthlete.gender());
        athlete.setIdentity(reqAthlete.identity());
        athlete.setHeight(reqAthlete.height());
        athlete.setWeight(reqAthlete.weight());
        athlete.setUserId(user.getId());
        athlete.setRole(Role.ATHLETE);

        try {
            return athleterepository.save(athlete);
        } catch (DuplicateKeyException e) {
            throw new AthleteCpfAlreadyExistsException(user.getCpf());
        }
  
    }

    public AthleteResponse findbyCpf(String cpf){
        Athlete athlete = athleterepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException(ATHLETE_NOT_FOUND_ID + cpf));

        return new AthleteResponse(
                athlete.getId(),
                athlete.getCpf(),
                athlete.getName(),
                athlete.getSocialname(),
                athlete.getEmail(),
                athlete.getBorn(),
                athlete.getGender(),
                athlete.getIdentity(),
                athlete.getHeight(),
                athlete.getWeight()
        );
    }

	 public Athlete updateAthlete(String id,  AthleteRequest updateAthlete){
           Athlete existAthlete = athleterepository.findById(id)
        .orElseThrow(() -> new RuntimeException(ATHLETE_NOT_FOUND_ID));

            Optional.ofNullable(updateAthlete.name())
                .ifPresent(existAthlete::setName);

         Optional.ofNullable(updateAthlete.socialName())
                 .ifPresent(existAthlete::setSocialname);

            Optional.ofNullable(updateAthlete.gender())
                .ifPresent(existAthlete::setGender);

            Optional.ofNullable(updateAthlete.identity())
                .ifPresent(existAthlete::setIdentity);

            Optional.ofNullable(updateAthlete.height())
                .ifPresent(existAthlete::setHeight);

            Optional.ofNullable(updateAthlete.weight())
                .ifPresent(existAthlete::setWeight);

                try {
                    return athleterepository.save(existAthlete);
                } catch (DuplicateKeyException e) {
                    throw new AthleteCpfAlreadyExistsException(existAthlete.getCpf());
                }
     }

     public Athlete pathAthlete(String cpf, AthleteRequest req){
            Athlete athlete = athleterepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException(ATHLETE_NOT_FOUND));


                if(req.name() != null){
                    athlete.setName(req.name());
                }

                 if(req.socialName() != null){
                     athlete.setSocialname(req.socialName());
                 }

                if(req.gender() != null){
                    athlete.setGender(req.gender());
                }

                if(req.identity() != null){
                    athlete.setIdentity(req.identity());
                }

                if(req.weight() != null){
                    athlete.setWeight(req.weight());
                }

                if(req.height() != null){
                    athlete.setHeight(req.height());
                }

                return athleterepository.save(athlete);
     }

    public void deleteAthlete(String id) {

      Athlete athlete = athleterepository.findById(id)
        .orElseThrow(() -> new RuntimeException(NOT_FOUND_ID_DELETE));
         String userId = athlete.getUserId();

        athleterepository.deleteById(id);   // 👈 primeiro
        loginRepository.deleteById(userId); // 👈 depois
     }

    void validateCpfAvailable(String cpf, String currentAthleteId) {
        if (cpf == null) {
            return;
        }

        athleterepository.findByCpf(cpf)
            .filter(athlete -> currentAthleteId == null || !currentAthleteId.equals(athlete.getId()))
            .ifPresent(athlete -> {
                throw new AthleteCpfAlreadyExistsException(cpf);
            });
    }
}
