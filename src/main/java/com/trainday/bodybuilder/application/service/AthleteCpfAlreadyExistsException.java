package com.trainday.bodybuilder.application.service;

public class AthleteCpfAlreadyExistsException extends RuntimeException {

    public AthleteCpfAlreadyExistsException(String cpf) {
        super("CPF already exists: " + cpf);
    }
}
