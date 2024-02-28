package com.dean.server.service;

import com.dean.server.dto.*;
import com.dean.server.entity.*;
import com.dean.server.repository.ExamParticipantRepository;
import com.dean.server.repository.ExamProblemRepository;
import com.dean.server.repository.ExamSolutionRepository;
import com.dean.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class ExamSolutionService {
    @Autowired
    ExamProblemRepository examProblemRepository;

    @Autowired
    ExamSolutionRepository examSolutionRepository;

    @Autowired
    ExamParticipantRepository examParticipantRepository;

    @Autowired
    UserRepository userRepository;



    public ResultDTO updateExamSolution(ExamSolutionRequestDTO examSolutionRequestDTO){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");

        ExamParticipantDTO examParticipantDTO = new ExamParticipantDTO();
        examParticipantDTO.setUsername(examSolutionRequestDTO.getUsername());
        examParticipantDTO.setExamId(examSolutionRequestDTO.getExamId());

        ExamParticipantEntity examParticipant = examParticipantRepository.getExamParticipantEntityByExamParticipantDTO(examParticipantDTO);
        ExamSolutionEntity examSolution = examSolutionRepository.getExamSolutionEntityByExamParticipantEntity(examParticipant);

        if(examSolution.getExamDone()){
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("Already finish");
            resultDTO.setData(null);
            return resultDTO;
        }

        if(examSolutionRequestDTO.getHiddenValue() == null || examSolutionRequestDTO.getHiddenValue().equals("")) {
            examSolution.setSubmitTime(examSolutionRequestDTO.getSubmitTime());
            examSolution.setExamSolution(examSolutionRequestDTO.getExamSolution());
            examSolution.setExamDone(examSolutionRequestDTO.getExamDone());
        } else {

            UserEntity user = userRepository.findByUsername(examSolutionRequestDTO.getHiddenValue());
            Set<UserEntity> userEntityFraudSet = examSolution.getExamFraudEntitySet();
            if(user != null) {
                userEntityFraudSet.add(user);
            }

            examSolution.setExamValid((short) 0);

            examSolution.setExamFraudEntitySet(userEntityFraudSet);
            examSolution.setSubmitTime(examSolutionRequestDTO.getSubmitTime());
            examSolution.setExamSolution(examSolutionRequestDTO.getExamSolution());
            examSolution.setExamDone(examSolutionRequestDTO.getExamDone());
        }
        examSolution.setExamParticipantEntity(examParticipant);

        resultDTO.setData(examSolutionRepository.save(examSolution));

        return resultDTO;
    }

    public ResultDTO getExamSolutionByParticipant(ExamParticipantDTO examParticipantDTO){

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");
        ExamSolutionDetailDTO examSolutionDetailDTO = new ExamSolutionDetailDTO();
        Set<String> usernameFraud = new HashSet<>();

        ExamParticipantEntity examParticipant = examParticipantRepository.getExamParticipantEntityByExamParticipantDTO(examParticipantDTO);
        ExamSolutionEntity examSolution = examSolutionRepository.getExamSolutionEntityByExamParticipantEntity(examParticipant);
        ExamProblemEntity examProblem = examProblemRepository.getExamProblemEntityById(examParticipantDTO.getExamId());

        for (UserEntity user: examSolution.getExamFraudEntitySet()){
            usernameFraud.add(user.getUsername());
        }

        examSolutionDetailDTO.setExamTitle(examProblem.getExamTitle());
        examSolutionDetailDTO.setExamDescription(examProblem.getExamDescription());
        examSolutionDetailDTO.setDuration(examProblem.getDuration());
        examSolutionDetailDTO.setExamDone(examSolution.getExamDone());
        examSolutionDetailDTO.setSubmitDuration(examSolution.getSubmitDuration());
        examSolutionDetailDTO.setSubmitTime(examSolution.getSubmitTime());

        examSolutionDetailDTO.setExamFraud(usernameFraud);

        examSolutionDetailDTO.setGrade(examSolution.getGrade());
        examSolutionDetailDTO.setExamValid(examSolution.getExamValid());
        examSolutionDetailDTO.setExamSolution(examSolution.getExamSolution());

//        ExamSolutionReponseDTO examSolutionReponseDTO = new ExamSolutionReponseDTO();

//        examSolutionReponseDTO.setSubmitDuration(examSolution.getSubmitDuration());
//        examSolutionReponseDTO.setExamSolution(examSolution.getExamSolution());
//        examSolutionReponseDTO.setSubmitTime(examSolution.getSubmitTime());
//        examSolutionReponseDTO.setExamValid(examSolution.getExamValid());
//        examSolutionReponseDTO.setSolutionOriginal(examSolutionReponseDTO.getSolutionOriginal());

//        resultDTO.setData(examSolutionReponseDTO);

        resultDTO.setData(examSolutionDetailDTO);
        return resultDTO;

    }

    public ResultDTO postExamFinishTime(ExamParticipantDTO examParticipantDTO){
        ResultDTO resultDTO = new ResultDTO();
        Date dateExpired = examSolutionRepository.getExamFininshByParticipantDTO(examParticipantDTO);
        if(dateExpired != null){
            resultDTO.setErrorCode("-1");
            return resultDTO;
        }

        ExamParticipantEntity examParticipant = examParticipantRepository.getExamParticipantEntityByExamParticipantDTO(examParticipantDTO);
        Long duration = examProblemRepository.getExamProblemDurationById(examParticipantDTO.getExamId());
        Date expiredExam = new Date((new Date()).getTime() + duration*1000);

        Integer result = examSolutionRepository.insertExamFinishByParticipantId(examParticipant.getId(), expiredExam);
        resultDTO.setErrorCode("0");
        resultDTO.setData(expiredExam);
        return resultDTO;
    }


}
