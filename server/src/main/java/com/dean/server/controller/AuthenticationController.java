package com.dean.server.controller;

import com.dean.server.dto.ExamPostDTO;
import com.dean.server.dto.LoginDTO;
import com.dean.server.dto.RegisterDTO;
import com.dean.server.service.AuthenticationService;
import com.dean.server.service.ExamService;
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
    private ExamService examService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        return authenticationService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        return authenticationService.login(loginDTO);
    }
    // need fix
    @PostMapping("/postExam")
    public ResponseEntity<?> postExam(@RequestBody ExamPostDTO examPostDTO){
        return examService.savePostExam(examPostDTO);
    }





}
