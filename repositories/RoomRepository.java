package com.chatapp.repositories;

import com.chatapp.entities.Message;
import com.chatapp.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    Optional<Room> findByRoomId(String roomId);

    boolean existsByRoomId(String roomId);

    List<Message> findAllMessagesByRoomId(String roomId);


}
