package com.trainday.bodybuilder.api.DTO.request;

public record LoginRequest(
    String email,
    String password
) {
}
