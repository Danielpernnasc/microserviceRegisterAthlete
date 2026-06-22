package com.trainday.bodybuilder.api.DTO.response;

public record LoginResponse(
        String id,
        String email,
        String cpf,
        java.time.LocalDate born
) {



}
