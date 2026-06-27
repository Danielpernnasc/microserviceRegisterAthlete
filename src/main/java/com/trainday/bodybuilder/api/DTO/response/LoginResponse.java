package com.trainday.bodybuilder.api.DTO.response;

import com.trainday.bodybuilder.domain.model.enums.Role;

public record LoginResponse(
        String id,
        String email,
        String cpf,
        java.time.LocalDate born,
         Role role
) {



}
