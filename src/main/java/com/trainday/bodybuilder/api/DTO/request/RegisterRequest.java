package com.trainday.bodybuilder.api.DTO.request;

import javax.swing.*;
import java.time.LocalDate;

public record RegisterRequest(
        String cpf,
        LocalDate born,
        String email,
        String password
) {

}
