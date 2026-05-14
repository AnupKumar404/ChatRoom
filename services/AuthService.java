package com.chatapp.services;

import com.chatapp.dto.LoginDto;
import com.chatapp.entities.User;
import com.chatapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(LoginDto dto) throws BadRequestException {
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new BadRequestException("Invalid username"));

//        log.info(dto.username());
        return user.getPassword().equals(dto.password());
    }
}
