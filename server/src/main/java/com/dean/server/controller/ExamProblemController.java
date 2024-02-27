package com.dean.server.controller;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.dto.ExamSolutionRequestDTO;
import com.dean.server.dto.ResultDTO;
import com.dean.server.service.ExamProblemService;
import com.dean.server.service.ExamSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@RestController
public class ExamProblemController {

    @Autowired
    private ExamProblemService examProblemService;

    @Autowired
    ExamSolutionService examSolutionService;



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
    public ResponseEntity<ResultDTO> postExamSolution(@RequestBody ExamSolutionRequestDTO examSolutionRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(examSolutionService.updateExamSolution(examSolutionRequestDTO));
    }

    @PostMapping(value = "/getExamSolution")
    public ResponseEntity<ResultDTO> getExamSolutionByExamParticipant(@RequestBody ExamParticipantDTO examParticipantDTO){
        return ResponseEntity.status(HttpStatus.OK).body(examSolutionService.getExamSolutionByParticipant(examParticipantDTO));
    }

    @PostMapping(value = "/postExamFinishTime")
    public ResponseEntity<ResultDTO> postExamFinishTime(@RequestBody ExamParticipantDTO examParticipantDTO){
        return ResponseEntity.status(HttpStatus.OK).body(examSolutionService.postExamFinishTime(examParticipantDTO));
    }


}
