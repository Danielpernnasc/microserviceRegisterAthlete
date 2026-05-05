package com.trainday.bodybuilder.application.service;


import java.util.Optional;

// import org.springframework.dao.DuplicateKeyException;
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
    private static final String ATHLETE_NOT_FOUND = "Athlete not found with id ";



    public AthleteService(
        AthleteRepository athleterepository,
        LoginRepository loginRepository
     
    ){
        this.athleterepository = athleterepository;
        this.loginRepository = loginRepository;
    
    }

    public Athlete createAthlete(AthleteRequest reqAthlete){
        Login user = loginRepository.findByEmail(reqAthlete.email())
               .orElseThrow(() -> new RuntimeException("User not found: " + reqAthlete.email()));

        validateCpfAvailable(reqAthlete.cpf(), null);
         
        Athlete athlete = new Athlete();
        athlete.setCPF(reqAthlete.cpf());
         athlete.setName(reqAthlete.name());
         athlete.setEmail(reqAthlete.email());
        athlete.setAge(reqAthlete.age());
        athlete.setGender(reqAthlete.gender());
        athlete.setIdentity(reqAthlete.identity());
        athlete.setHeight(reqAthlete.height());
        athlete.setWeight(reqAthlete.weight());
        athlete.setpercentageFat(reqAthlete.percentagefat());
        athlete.setUserId(user.getId());

        try {
            return athleterepository.save(athlete);
        } catch (DuplicateKeyException e) {
            throw new AthleteCpfAlreadyExistsException(reqAthlete.cpf());
        }
  
    }
    public AthleteResponse getAthleteById(String id) {
        Athlete athlete = athleterepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ATHLETE_NOT_FOUND + id));

        return new AthleteResponse(
            athlete.getId(),
            athlete.getCPF(),
            athlete.getName(),
            athlete.getEmail(),
            athlete.getAge(),
            athlete.getGender(),
            athlete.getIdentity(),
            athlete.getHeight(),
            athlete.getWeight(),
            athlete.getpercentageFat()
        );
    }

	 public Athlete updateAthlete(String id,  AthleteRequest updateAthlete){
           Athlete existAthlete = athleterepository.findById(id)
        .orElseThrow(() -> new RuntimeException(ATHLETE_NOT_FOUND));

            Optional.ofNullable(updateAthlete.cpf())
                .ifPresent(cpf -> {
                    validateCpfAvailable(cpf, id);
                    existAthlete.setCPF(cpf);
                });
                // .ifPresent(existAthlete::setCPF);
            Optional.ofNullable(updateAthlete.name())
                .ifPresent(existAthlete::setName);
                
            Optional.ofNullable(updateAthlete.age())
                .ifPresent(existAthlete::setAge);

            Optional.ofNullable(updateAthlete.gender())
                .ifPresent(existAthlete::setGender);

            Optional.ofNullable(updateAthlete.identity())
                .ifPresent(existAthlete::setIdentity);

            Optional.ofNullable(updateAthlete.email())
                .ifPresent(existAthlete::setEmail);

            Optional.ofNullable(updateAthlete.height())
                .ifPresent(existAthlete::setHeight);

            Optional.ofNullable(updateAthlete.weight())
                .ifPresent(existAthlete::setWeight);

            Optional.ofNullable(updateAthlete.percentagefat())
                .ifPresent(existAthlete::setpercentageFat);

                
                try {
                    return athleterepository.save(existAthlete);
                } catch (DuplicateKeyException e) {
                    throw new AthleteCpfAlreadyExistsException(existAthlete.getCPF());
                }
     }

    public void deleteAthlete(String id) {

      Athlete athlete = athleterepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not found the id for Delete"));
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
