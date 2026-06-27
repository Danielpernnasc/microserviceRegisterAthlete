package com.trainday.bodybuilder.api.DTO.request;

import com.trainday.bodybuilder.domain.model.enums.Role;

import javax.swing.*;
import java.time.LocalDate;

public record RegisterRequest(
        String cpf,
        LocalDate born,
        String email,
        String password,
        Role role
) {

}
