package com.dean.server.service;


import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.dto.ResultDTO;
import com.dean.server.entity.ExamParticipantEntity;
import com.dean.server.entity.ExamProblemEntity;
import com.dean.server.entity.UserEntity;
import com.dean.server.repository.ExamParticipantRepository;
import com.dean.server.repository.ExamProblemRepository;
import com.dean.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExamProblemService {

    @Autowired
    private ExamProblemRepository examProblemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamParticipantRepository examParticipantRepository;

    public ResultDTO saveExamProblem(ExamProblemDTO examProblemDTO) {
        ResultDTO resultDTO = new ResultDTO();
        ExamProblemEntity examProblemEntity = new ExamProblemEntity();
        Set<ExamParticipantEntity> examParticipantEntitySet = new HashSet<>();

        examProblemEntity.setExamTitle(examProblemDTO.getExamTitle());
        examProblemEntity.setExamDescription(examProblemDTO.getExamDescription());
        examProblemEntity.setCreateBy(examProblemDTO.getCreateBy());
        examProblemEntity.setStartTime(examProblemDTO.getStartTime());
        examProblemEntity.setEndTime(examProblemDTO.getEndTime());
        examProblemEntity.setDuration(Duration.ofMillis(examProblemDTO.getDuration()));


        for(String student : examProblemDTO.getExamParticipantSet()){
            UserEntity userEntity = userRepository.findByUsername(student);
            if(userEntity != null){
                ExamParticipantEntity examParticipantEntity = new ExamParticipantEntity();
                examParticipantEntity.setStudent(userEntity);
                examParticipantEntity.setExamProblem(examProblemEntity);

                examParticipantEntitySet.add(examParticipantEntity);
            }
        }
        examProblemEntity.setExamParticipantEntitySet(examParticipantEntitySet);

        ExamProblemEntity examProblemEntityResult = examProblemRepository.save(examProblemEntity);

        resultDTO.setData(examProblemEntityResult);
        resultDTO.setErrorCode("0");
        resultDTO.setMessage("Thành công");

        return resultDTO;


    }
}
