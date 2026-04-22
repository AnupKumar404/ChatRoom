package com.chatapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message{

    public String sender;

    public String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    public LocalTime time;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
