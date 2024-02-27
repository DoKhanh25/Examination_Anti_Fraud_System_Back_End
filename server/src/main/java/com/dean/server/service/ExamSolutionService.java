package com.dean.server.service;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.dto.ExamSolutionRequestDTO;
import com.dean.server.dto.ExamSolutionReponseDTO;
import com.dean.server.dto.ResultDTO;
import com.dean.server.entity.ExamParticipantEntity;
import com.dean.server.entity.ExamSolutionEntity;
import com.dean.server.repository.ExamParticipantRepository;
import com.dean.server.repository.ExamProblemRepository;
import com.dean.server.repository.ExamSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class ExamSolutionService {
    @Autowired
    ExamProblemRepository examProblemRepository;

    @Autowired
    ExamSolutionRepository examSolutionRepository;

    @Autowired
    ExamParticipantRepository examParticipantRepository;



    public ResultDTO updateExamSolution(ExamSolutionRequestDTO examSolutionRequestDTO){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");

        ExamParticipantDTO examParticipantDTO = new ExamParticipantDTO();
        examParticipantDTO.setUsername(examSolutionRequestDTO.getUsername());
        examParticipantDTO.setExamId(examSolutionRequestDTO.getExamId());

        ExamParticipantEntity examParticipant = examParticipantRepository.getExamParticipantEntityByExamParticipantDTO(examParticipantDTO);
        ExamSolutionEntity examSolution = examSolutionRepository.getExamSolutionEntityByExamParticipantEntity(examParticipant);

        if(examSolutionRequestDTO.getHiddenValue() == null || examSolutionRequestDTO.getHiddenValue().equals("")){
            examSolution.setSubmitTime(examSolutionRequestDTO.getSubmitTime());
            examSolution.setExamSolution(examSolutionRequestDTO.getExamSolution());
            examSolution.setExamDone(examSolutionRequestDTO.getExamDone());
        } else {
            examSolution.setExamValid((short) 0);
            examSolution.setSolutionOriginal(examSolutionRequestDTO.getHiddenValue());
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
        ExamSolutionReponseDTO examSolutionReponseDTO = new ExamSolutionReponseDTO();

        ExamParticipantEntity examParticipant = examParticipantRepository.getExamParticipantEntityByExamParticipantDTO(examParticipantDTO);
        ExamSolutionEntity examSolution = examSolutionRepository.getExamSolutionEntityByExamParticipantEntity(examParticipant);

        examSolutionReponseDTO.setSubmitDuration(examSolution.getSubmitDuration());
        examSolutionReponseDTO.setExamSolution(examSolution.getExamSolution());
        examSolutionReponseDTO.setSubmitTime(examSolution.getSubmitTime());
        examSolutionReponseDTO.setExamValid(examSolution.getExamValid());
        examSolutionReponseDTO.setSolutionOriginal(examSolutionReponseDTO.getSolutionOriginal());

        resultDTO.setData(examSolutionReponseDTO);
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
