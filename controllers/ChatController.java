package com.chatapp.controllers;

import com.chatapp.configs.AppConstants;
import com.chatapp.dto.MessageRequest;
import com.chatapp.dto.TypingEvent;
import com.chatapp.entities.Message;
import com.chatapp.entities.MessageRecord;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.repositories.MessageRepository;
import com.chatapp.repositories.RoomRepository;
import com.chatapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@CrossOrigin(AppConstants.FRONT_END_URL)
@Slf4j
public class ChatController {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public ChatController(UserRepository userRepository, RoomRepository roomRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    // For sending and receiving messages
    @MessageMapping("/sendMessage/{roomId}")// app/sendMessage/roomId
    @SendTo("/topic/room/{roomId}")// subscribe
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
            ) throws ResourceNotFoundException, BadRequestException {

            if(!roomRepository.existsByRoomId(roomId)){
                throw new BadRequestException("Invalid username or roomId");
            }

            Message message = new Message();
            message.setSender(request.sender());
            message.setContent(request.content());
            message.setDate(LocalDate.now());
            message.setTime(LocalTime.now());

            MessageRecord messageRecord = messageRepository.findByRoomId(roomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid room"));

            messageRecord.setRoomId(roomId);
            messageRecord.getMessages().add(message);

            messageRepository.save(messageRecord);
            return message;
        }

    @MessageMapping("/sendTyping/{roomId}")
    @SendTo("/topic/broadcastTyping/{roomId}")
    public TypingEvent broadcastTyping(
            @DestinationVariable String roomId,
            @Payload TypingEvent payload
    ){
        return payload;
    }

}



