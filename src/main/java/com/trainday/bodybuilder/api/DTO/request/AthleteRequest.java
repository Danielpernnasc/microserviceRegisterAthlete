package com.trainday.bodybuilder.api.DTO.request;

import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;
import com.trainday.bodybuilder.domain.model.enums.Role;

import java.time.LocalDate;

public record AthleteRequest(
        String name,
        String socialName,
        Gender gender,
        GenderIdentity identity,
        Double height,
        Double weight
){
  
}    



