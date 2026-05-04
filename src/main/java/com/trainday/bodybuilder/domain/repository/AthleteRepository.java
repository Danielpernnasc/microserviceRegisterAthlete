package com.trainday.bodybuilder.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainday.bodybuilder.domain.model.Athlete;



@Repository
public interface AthleteRepository extends MongoRepository<Athlete, String> {




    Optional<Athlete> findByUserId(String userId);

    Optional<Athlete> findByEmail(String email);

    //Optional<Athlete> findByCpf(String cpf);

    Optional<Athlete> findByIdAndEmail(String id, String email);


}
