package com.trainday.bodybuilder.api.DTO.request;

import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;

public record AthleteRequest(
    String cpf,
    String name,
    String email,
    Long age,
    Gender gender,
    GenderIdentity identity,
    Double height,
    Double weight,
    Long percentagefat
){
  
}    



