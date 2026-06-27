package com.trainday.bodybuilder.api.DTO.response;

import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;
import com.trainday.bodybuilder.domain.model.enums.Role;

import java.time.LocalDate;

public record AthleteResponse(
        String id,
        String cpf,
        String name,
        String socialName,
        String email,
        LocalDate born,
        Gender gender,
        GenderIdentity identity,
        Double height,
        Double weight,
        Role role

) {}

    