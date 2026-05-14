package com.chatapp.controllers;

import com.chatapp.dto.LoginDto;
import com.chatapp.services.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Boolean> login(LoginDto dto) throws BadRequestException {
        return new ResponseEntity<>(authService.login(dto), HttpStatus.OK);
    }
}
