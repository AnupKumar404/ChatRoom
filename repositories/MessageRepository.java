package com.chatapp.repositories;

import com.chatapp.entities.Message;
import com.chatapp.entities.MessageRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends MongoRepository<MessageRecord, String> {
    boolean existsByRoomId(String roomId);
    Optional<MessageRecord> findByRoomId(String roomId);
}
