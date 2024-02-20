package com.dean.server.controller;

import com.dean.server.dto.ExamPostDTO;
import com.dean.server.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2")
@RestController
public class ExamController {
    Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;



}
