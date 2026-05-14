package com.chatapp.services;

import com.chatapp.dto.UserRequestDto;
import com.chatapp.entities.User;
import com.chatapp.enums.UserRole;
import com.chatapp.exceptions.DuplicateDataException;
import com.chatapp.exceptions.ResourceNotFoundException;
import com.chatapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserRequestDto dto){
       if(userRepository.existsByUsername(dto.username())){
           throw new DuplicateDataException("username already exists");
       }

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setFullName(dto.fullName());
        user.setRole(UserRole.USER);
        user.setOnline(true);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }


    public User getUser(String username) throws ResourceNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Username"));
    }

    public List<User> fetchAllUsers(int page, int size){

        List<User> users = userRepository.findAll();

        int start = Math.max(0, users.size() - (page + 1) * size);
        int end = Math.min(users.size(), start + size);
        return users.subList(start, end);
    }
}