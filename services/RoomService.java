package com.chatapp.services;

import com.chatapp.dto.JoinDetailDto;
import com.chatapp.entities.Room;
import com.chatapp.entities.User;
import com.chatapp.enums.UserRole;
import com.chatapp.exceptions.DuplicateDataException;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.repositories.RoomRepository;
import com.chatapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomService(RoomRepository roomRepository,
                       UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Room createRoom(JoinDetailDto dto) throws ResourceNotFoundException {

        if(roomRepository.existsByRoomId(dto.roomId())){
            throw new DuplicateDataException("room already exists");
        }

        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Username"));

        Room room = new Room();
        room.setRoomId(dto.roomId());
        room.setUsers(Set.of(user));
        return roomRepository.save(room);
    }

    public Room getRoom(String roomId) throws ResourceNotFoundException {

        return roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    public JoinDetailDto joinRoom(JoinDetailDto dto) throws BadRequestException {
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new BadRequestException("Invalid username"));

        Room room = roomRepository.findByRoomId(dto.roomId())
                .orElseThrow(() -> new BadRequestException("Invalid roomId"));

        return new JoinDetailDto(user.getUsername(), room.getRoomId());
    }
}
