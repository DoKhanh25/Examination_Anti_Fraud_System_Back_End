package com.dean.server.controller;

import com.dean.server.dto.ExamPostDTO;
import com.dean.server.dto.LoginDTO;
import com.dean.server.dto.RegisterDTO;
import com.dean.server.security.JwtUtil;
import com.dean.server.service.AccountService;
import com.dean.server.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/api/authentication")
@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ExamService examService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        logger.info(registerDTO.getUsername());
        return accountService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        logger.info(loginDTO.getUsername());
        return accountService.login(loginDTO);
    }
    @PostMapping("/postExam")
    public ResponseEntity<?> postExam(@RequestBody ExamPostDTO examPostDTO){
        logger.info(examPostDTO.toString());
        return examService.savePostExam(examPostDTO);
    }

    @GetMapping("/sampleAccountFile")
    public ResponseEntity<?> getSampleAccountCreateFile(){
        Resource accountCreateFileResource = new ClassPathResource("/static/account_create_sample.xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + accountCreateFileResource.getFilename())
                .body(accountCreateFileResource);
    }


    @PostMapping("/uploadAccountFile")
    public ResponseEntity<?> uploadAccountFile(@RequestParam("file") MultipartFile file){
        accountService.saveAccountsByExcel(file);
        return null;
    }


}
