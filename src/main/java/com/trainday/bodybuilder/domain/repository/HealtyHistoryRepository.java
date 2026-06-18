package com.trainday.bodybuilder.domain.repository;


import com.trainday.bodybuilder.domain.model.HealthyHistory;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

public interface HealtyHistoryRepository extends MongoRepository<HealthyHistory, String> {
    Optional<HealthyHistory>  findByAthleteCpf(String athleteCpf);

}
