package com.dean.server.controller;

import com.dean.server.dto.ExamPostDTO;
import com.dean.server.dto.LoginRequestDTO;
import com.dean.server.dto.RegisterDTO;
import com.dean.server.security.JwtUtil;
import com.dean.server.service.AuthenticationService;
import com.dean.server.service.ExamService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/authentication")
@RestController
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        logger.info("Registering user: " + registerDTO.getUsername());
        return authenticationService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        logger.info("Logging in user: " + loginRequestDTO.getUsername());
        return authenticationService.login(loginRequestDTO);
    }






}
