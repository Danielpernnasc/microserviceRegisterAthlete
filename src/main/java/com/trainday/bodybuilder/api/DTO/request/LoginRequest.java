package com.trainday.bodybuilder.api.DTO.request;

public record LoginRequest(
    String login,
    String password
) {
}
