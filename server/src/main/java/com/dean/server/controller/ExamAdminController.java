package com.dean.server.controller;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.dto.GradeDTO;
import com.dean.server.security.JwtUtil;
import com.dean.server.service.ExamProblemService;
import com.dean.server.service.ExamSolutionService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@RestController
public class ExamAdminController {

    @Autowired
    private ExamProblemService examProblemService;

    @Autowired
    ExamSolutionService examSolutionService;

    @Autowired
    JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(ExamAdminController.class);

    @PostMapping(value = "/saveExamProblem")
    public ResponseEntity<?> saveExamProblem(
            @RequestBody ExamProblemDTO examProblemDTO,
            HttpServletRequest request){

        Boolean isAdminRole = jwtUtil.isAdminRole(request);
        if (!isAdminRole){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(examProblemService.saveExamProblem(examProblemDTO));
    }

    @GetMapping(value = "/getAllExamProblem")
    public ResponseEntity<?> getAllExamProblem(HttpServletRequest request){

        Boolean isAdminRole = jwtUtil.isAdminRole(request);
        if (!isAdminRole){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return ResponseEntity.status(HttpStatus.OK).body(examProblemService.getAllExamProblem());
    }


    @PostMapping(value = "/getExamSolution")
    public ResponseEntity<?> getExamSolutionByExamParticipant(
            @RequestBody ExamParticipantDTO examParticipantDTO,
            HttpServletRequest request){

        Boolean isAdminRole = jwtUtil.isAdminRole(request);
        if (!isAdminRole){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return ResponseEntity.status(HttpStatus.OK).body(examSolutionService.getExamSolutionByParticipant(examParticipantDTO));
    }

    @PostMapping(value = "/postGrade")
    public ResponseEntity<?> postGrade(
            @RequestBody GradeDTO gradeDTO,
            HttpServletRequest request){

        Boolean isAdminRole = jwtUtil.isAdminRole(request);
        if (!isAdminRole){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return ResponseEntity.status(HttpStatus.OK).body(examSolutionService.postGrade(gradeDTO));
    }




}
