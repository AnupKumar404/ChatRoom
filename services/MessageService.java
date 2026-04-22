package com.chatapp.services;

import com.chatapp.entities.Message;
import com.chatapp.entities.MessageRecord;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.repositories.MessageRepository;
import com.chatapp.repositories.RoomRepository;
import com.chatapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

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

        MessageRecord messageRecord = messageRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid roomId"));

        List<Message> messages = messageRecord.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);

        return messages.subList(start, end);
    }

    public String removeMessages(String roomId) throws ResourceNotFoundException {

        MessageRecord messageRecord = messageRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid room"));

        List<Message> messages = messageRecord.getMessages();
        messageRecord.getMessages().removeAll(messages);
        messageRepository.save(messageRecord);
        return "Successfully deletes messages";
    }
}
