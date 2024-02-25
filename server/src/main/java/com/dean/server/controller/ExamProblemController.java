package com.dean.server.controller;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.dto.ResultDTO;
import com.dean.server.service.ExamProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@RestController
public class ExamProblemController {

    @Autowired
    private ExamProblemService examProblemService;



    @PostMapping(value = "/saveExamProblem")
    public ResponseEntity<ResultDTO> saveExamProblem(@RequestBody ExamProblemDTO examProblemDTO){
        return  ResponseEntity.status(HttpStatus.OK).body(examProblemService.saveExamProblem(examProblemDTO));
    }

    @GetMapping(value = "/getAllExamProblem")
    public ResponseEntity<ResultDTO> getAllExamProblem(){
        return ResponseEntity.status(HttpStatus.OK).body(examProblemService.getAllExamProblem());
    }

    @GetMapping(value = "/getExamProblem/{username}")
    public ResponseEntity<ResultDTO> getExamProblemByUsername(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(examProblemService.getExamProblemByUsername(username));
    }

    @PostMapping(value = "/getExamDetail")
    public ResponseEntity<ResultDTO> getExamDetailByParticipant(@RequestBody ExamParticipantDTO examParticipantDTO){
        return ResponseEntity.status(HttpStatus.OK).body(examProblemService.getExamDetailByParticipant(examParticipantDTO));
    }

    @PostMapping(value = "/postExamSolution")
    public ResponseEntity<ResultDTO> postExamSolution(@RequestBody ExamParticipantDTO examParticipantDTO){
        return ResponseEntity.status(HttpStatus.OK).body(examProblemService.getExamDetailByParticipant(examParticipantDTO));
    }
}
