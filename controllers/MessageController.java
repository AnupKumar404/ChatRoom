package com.chatapp.controllers;

import com.chatapp.entities.Message;
import com.chatapp.entities.MessageRecord;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId,
                                                           @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(messageService.getMessages(roomId, page, size));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> removeMessages(@PathVariable String roomId) throws ResourceNotFoundException {
        return ResponseEntity.ok(messageService.removeMessages(roomId));
    }


}
