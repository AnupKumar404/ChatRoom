package com.chatapp.repositories;

import com.chatapp.entities.MessageRecord;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MessageRepository extends MongoRepository<MessageRecord, String> {
    boolean existsByRoomId(String roomId);
    MessageRecord findByRoomId(String roomId);
}
