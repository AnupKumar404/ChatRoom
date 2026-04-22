package com.chatapp.dto;

public record UserRequestDto(
        String username,
        String password,
        String fullName
) {
}
