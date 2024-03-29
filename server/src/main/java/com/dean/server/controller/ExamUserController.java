package com.dean.server.controller;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.dto.ExamSolutionRequestDTO;
import com.dean.server.security.JwtUtil;
import com.dean.server.service.ExamProblemService;
import com.dean.server.service.ExamSolutionService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class ExamUserController {
    @Autowired
    private ExamProblemService examProblemService;

    @Autowired
    ExamSolutionService examSolutionService;

    @Autowired
    JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(ExamUserController.class);

    @GetMapping(value = "/getExamProblem/{username}")
    public ResponseEntity<?> getExamProblemByUsername(
            @PathVariable("username") String username,
            HttpServletRequest request){

        String token = jwtUtil.getToken(request);
        String usernameFromToken = jwtUtil.extractUsername(token);

        if(!username.equals(usernameFromToken)){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return ResponseEntity.status(HttpStatus.OK).body(examProblemService.getExamProblemByUsername(username));
    }

    @PostMapping(value = "/getExamDetail")
    public ResponseEntity<?> getExamDetailByParticipant(
            @RequestBody ExamParticipantDTO examParticipantDTO,
            HttpServletRequest request){

        String token = jwtUtil.getToken(request);
        String usernameFromToken = jwtUtil.extractUsername(token);

        if(!examParticipantDTO.getUsername().equals(usernameFromToken)){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return ResponseEntity.status(HttpStatus.OK).body(examProblemService.getExamDetailByParticipant(examParticipantDTO));
    }

    @PostMapping(value = "/postExamSolution")
    public ResponseEntity<?> postExamSolution(
            @RequestBody ExamSolutionRequestDTO examSolutionRequestDTO,
            HttpServletRequest request){

        String token = jwtUtil.getToken(request);
        String usernameFromToken = jwtUtil.extractUsername(token);

        if(!examSolutionRequestDTO.getUsername().equals(usernameFromToken)){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return ResponseEntity.status(HttpStatus.OK).body(examSolutionService.updateExamSolution(examSolutionRequestDTO));
    }

    @PostMapping(value = "/postExamFinishTime")
    public ResponseEntity<?> postExamFinishTime(
            @RequestBody ExamParticipantDTO examParticipantDTO,
            HttpServletRequest request){

        String token = jwtUtil.getToken(request);
        String usernameFromToken = jwtUtil.extractUsername(token);

        if(!examParticipantDTO.getUsername().equals(usernameFromToken)){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        return ResponseEntity.status(HttpStatus.OK).body(examSolutionService.postExamFinishTime(examParticipantDTO));
    }

}
