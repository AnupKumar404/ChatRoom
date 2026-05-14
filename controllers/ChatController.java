package com.chatapp.controllers;

import com.chatapp.dto.MessageRequest;
import com.chatapp.dto.TypingEvent;
import com.chatapp.entities.Message;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    // For sending and receiving messages
    @MessageMapping("/sendMessage/{roomId}")// app/sendMessage/roomId
    @SendTo("/topic/room/{roomId}")// subscribe
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
            ) throws ResourceNotFoundException, BadRequestException {

        return messageService.sendMessage(roomId, request);

        }

    @MessageMapping("/sendTyping/{roomId}")
    @SendTo("/topic/broadcastTyping/{roomId}")
    public TypingEvent broadcastTyping(
            @Payload TypingEvent payload
    ){
        return payload;
    }

}