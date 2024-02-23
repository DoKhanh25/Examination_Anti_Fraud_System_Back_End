package com.dean.server.controller;

import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.service.ExamProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
public class ExamProblemController {

    @Autowired
    private ExamProblemService examProblemService;

    @PostMapping(value = "/saveExamProblem")
    public ResponseEntity<?> saveExamProblem(@RequestBody ExamProblemDTO examProblemDTO){
        return  ResponseEntity.status(HttpStatus.OK).body(examProblemService.saveExamProblem(examProblemDTO));
    }
}
