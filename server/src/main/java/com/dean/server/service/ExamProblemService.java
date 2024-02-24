package com.dean.server.service;


import com.dean.server.dto.ExamInformationDTO;
import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.dto.ExamStudentDTO;
import com.dean.server.dto.ResultDTO;
import com.dean.server.entity.ExamParticipantEntity;
import com.dean.server.entity.ExamProblemEntity;
import com.dean.server.entity.UserEntity;
import com.dean.server.repository.ExamParticipantRepository;
import com.dean.server.repository.ExamProblemRepository;
import com.dean.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class ExamProblemService {
    Logger logger = LoggerFactory.getLogger(ExamProblemService.class);

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


        if(examProblemDTO.getExamParticipantSet() == null){
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("No Participant Data");
            return resultDTO;
        }


        examProblemEntity.setExamTitle(examProblemDTO.getExamTitle());
        examProblemEntity.setExamDescription(examProblemDTO.getExamDescription());
        examProblemEntity.setCreateBy(examProblemDTO.getCreateBy());
        examProblemEntity.setStartTime(examProblemDTO.getStartTime());
        examProblemEntity.setEndTime(examProblemDTO.getEndTime());
        examProblemEntity.setDuration(Duration.ofMillis(examProblemDTO.getDuration()));


        for(String student : examProblemDTO.getExamParticipantSet()){
            UserEntity userEntityTemp = userRepository.findByUsername(student);
            if(userEntityTemp != null){
                ExamParticipantEntity examParticipantEntityTemp = new ExamParticipantEntity();
                examParticipantEntityTemp.setStudent(userEntityTemp);
                examParticipantEntitySet.add(examParticipantEntityTemp);

            }
        }
        examProblemEntity.setExamParticipantEntitySet(examParticipantEntitySet);

        ExamProblemEntity examProblemEntityResult = examProblemRepository.save(examProblemEntity);

        for(ExamParticipantEntity e : examParticipantEntitySet){
            e.setExamProblem(examProblemEntityResult);
        }

        examParticipantRepository.saveAll(examParticipantEntitySet);

        resultDTO.setData(examProblemEntityResult);
        resultDTO.setErrorCode("0");
        resultDTO.setMessage("Thành công");

        return resultDTO;


    }

    public ResultDTO getAllExamProblem(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");
        List<List<Object>> listData = examProblemRepository.getAllExamProblemWithStudent();
        List<ExamInformationDTO> examInformationDTOList = new ArrayList<>();

        for (List<Object> objectList: listData){
            ExamInformationDTO examInformationDTO = new ExamInformationDTO();
            examInformationDTO.setCreateBy((String) objectList.get(0));
            examInformationDTO.setExamDescription((String) objectList.get(1));
            examInformationDTO.setExamTitle((String) objectList.get(2));
            examInformationDTO.setStartTime((Date) objectList.get(3));
            examInformationDTO.setEndTime((Date) objectList.get(4));
            examInformationDTO.setUsername((String) objectList.get(5));
            examInformationDTO.setDuration(((Duration) objectList.get(6)).toMillis());
            examInformationDTO.setExamSolution((String) objectList.get(7));

            examInformationDTOList.add(examInformationDTO);
        }
        resultDTO.setData(examInformationDTOList);
        return resultDTO;
    }

    public ResultDTO getExamProblemByUsername(String username){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");
        List<List<Object>> listData = examProblemRepository.getExamProblemByUsername(username);
        List<ExamStudentDTO> examStudentDTOList = new ArrayList<>();
        for(List<Object> objectList : listData){
            ExamStudentDTO examStudentDTO = new ExamStudentDTO();
            examStudentDTO.setUsername((String) objectList.get(0));
            examStudentDTO.setMsv((String) objectList.get(1));
            examStudentDTO.setStartTime((Date) objectList.get(2));
            examStudentDTO.setEndTime((Date) objectList.get(3));
            examStudentDTO.setDuration(((Duration) objectList.get(4)).toMillis());
            examStudentDTO.setCreateBy((String) objectList.get(5));
            examStudentDTO.setExamTitle((String) objectList.get(6));

            examStudentDTOList.add(examStudentDTO);
        }

        resultDTO.setData(examStudentDTOList);

        return resultDTO;
    }
}
