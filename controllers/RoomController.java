package com.chatapp.controllers;

import com.chatapp.dto.JoinDetailDto;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.services.RoomService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    //create room
    @PostMapping
    public ResponseEntity<?> createNewRoom(@RequestBody JoinDetailDto dto) throws ResourceNotFoundException {
        return new ResponseEntity<>(roomService.createRoom(dto), HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joiningRoom(@RequestBody JoinDetailDto dto) throws BadRequestException {
        return new ResponseEntity<>(roomService.joinRoom(dto), HttpStatus.OK);
    }

    //get room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId) throws ResourceNotFoundException {
        return ResponseEntity.ok(roomService.getRoom(roomId));
    }
}
