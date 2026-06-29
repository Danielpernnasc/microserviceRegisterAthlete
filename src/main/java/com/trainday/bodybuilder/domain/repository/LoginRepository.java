package com.trainday.bodybuilder.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainday.bodybuilder.domain.model.Login;

@Repository
public interface LoginRepository extends MongoRepository<Login, String>  {

     Optional<Login> findByCpf(String cpf);

     Optional<Login> findByEmail(String email);

     Boolean existsByEmail(String email);
     Boolean existsByCpf(String cpf);


}
