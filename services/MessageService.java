package com.chatapp.services;

import com.chatapp.dto.MessageRequest;
import com.chatapp.entities.Message;
import com.chatapp.entities.MessageRecord;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.repositories.MessageRepository;
import com.chatapp.repositories.RoomRepository;
import com.chatapp.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    public MessageService(MessageRepository messageRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public List<Message> getMessages(String roomId, int page, int size) throws ResourceNotFoundException {

        MessageRecord messageRecord = messageRepository.findByRoomId(roomId);

        List<Message> messages = messageRecord.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);

        return messages.subList(start, end);
    }

    public String removeMessages(String roomId) throws ResourceNotFoundException {

        MessageRecord messageRecord = messageRepository.findByRoomId(roomId);

        List<Message> messages = messageRecord.getMessages();
        messageRecord.getMessages().removeAll(messages);
        messageRepository.save(messageRecord);
        return "Successfully deletes messages";
    }

    public Message sendMessage(String roomId, MessageRequest request) throws BadRequestException {
        if(!roomRepository.existsByRoomId(roomId)){
            throw new BadRequestException("Invalid username or roomId");
        }

        Message message = new Message();
        message.setSender(request.sender());
        message.setContent(request.content());
        message.setDate(LocalDate.now());
        message.setTime(LocalTime.now());

        MessageRecord messageRecord = messageRepository.findByRoomId(roomId);

        if(messageRecord != null) {

            messageRecord.setRoomId(roomId);
            messageRecord.getMessages().add(message);
            messageRepository.save(messageRecord);

        }else{
            MessageRecord newRecord = new MessageRecord();
            newRecord.setRoomId(roomId);
            newRecord.getMessages().add(message);
            messageRepository.save(newRecord);
        }

        return message;
    }
}
