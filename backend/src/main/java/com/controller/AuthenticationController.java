package com.controller;

import com.dto.LoginDTO;
import com.service.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /////
    //Init
    /////
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /////
    //Methods
    /////

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.ok("Bearer " + token);
    }
}

