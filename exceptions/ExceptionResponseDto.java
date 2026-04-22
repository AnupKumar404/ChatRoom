package com.chatapp.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ExceptionResponseDto {

    public int status;
    public String error;
    public String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime timeStamp;
    public String path;

    public ExceptionResponseDto(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timeStamp = LocalDateTime.now();
    }
}
