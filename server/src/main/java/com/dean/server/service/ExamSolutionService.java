package com.dean.server.service;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.dto.ExamSolutionDTO;
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



    public ResultDTO updateExamSolution(ExamSolutionDTO examSolutionDTO){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");

        ExamParticipantEntity examParticipant = examParticipantRepository.getExamParticipantByExamIdAndUsername(examSolutionDTO.getExamId(), examSolutionDTO.getUsername());
        ExamSolutionEntity examSolution = examSolutionRepository.getExamSolutionEntityByExamParticipantEntity(examParticipant);

        if(examSolutionDTO.getHiddenValue() == null || examSolutionDTO.getHiddenValue() == ""){
            examSolution.setExamValid((short) 0);
            examSolution.setSolutionOriginal(null);
            examSolution.setSubmitTime(examSolutionDTO.getSubmitTime());
            examSolution.setSubmitDuration(examSolutionDTO.getSubmitDuration());
            examSolution.setExamSolution(examSolutionDTO.getExamSolution());
        } else {
            examSolution.setExamValid((short) 1);
            examSolution.setSolutionOriginal(examSolutionDTO.getHiddenValue());
            examSolution.setSubmitTime(examSolutionDTO.getSubmitTime());
            examSolution.setSubmitDuration(examSolutionDTO.getSubmitDuration());
            examSolution.setExamSolution(examSolutionDTO.getExamSolution());
        }
        resultDTO.setData(examSolutionRepository.save(examSolution));

        return resultDTO;
    }

    public ResultDTO getExamSolutionByParticipant(ExamParticipantDTO examParticipantDTO){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");
        ExamSolutionDTO examSolutionDTO = new ExamSolutionDTO();


        ExamParticipantEntity examParticipant = examParticipantRepository.getExamParticipantByExamIdAndUsername(examParticipantDTO.getExamId(), examParticipantDTO.getUsername());
        ExamSolutionEntity examSolution = examSolutionRepository.getExamSolutionEntityByParticipantId(examParticipant.getId());

        examSolutionDTO.setExamSolution(examSolution.getExamSolution());
        examSolutionDTO.setSubmitTime(examSolution.getSubmitTime());
        examSolutionDTO.setHiddenValue(examSolutionDTO.getHiddenValue());

        resultDTO.setData(examSolutionDTO);
        return resultDTO;

    }
}
