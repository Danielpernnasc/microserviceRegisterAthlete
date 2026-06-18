package com.trainday.bodybuilder.application.service;

public class HHbyCpfAlreadyExistsException extends RuntimeException {
    public HHbyCpfAlreadyExistsException(String athleteCpf) {
        super("HH already exists: " + athleteCpf);
    }
}
