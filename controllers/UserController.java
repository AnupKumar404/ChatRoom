package com.chatapp.controllers;

import com.chatapp.dto.UserRequestDto;
import com.chatapp.entities.User;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDto dto){
        return new ResponseEntity<>(userService.registerUser(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam String username) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<List<User>> fetchAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "50", required = false) int size){
        return new ResponseEntity<>(userService.fetchAllUsers(page, size), HttpStatus.OK);
    }
}
