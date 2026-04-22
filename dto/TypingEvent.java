package com.chatapp.dto;

public record TypingEvent(
        String username,
        String roomId,
        boolean isTyping
) {
}
