package com.dean.server.service;

import com.dean.server.dto.ExamPostDTO;
import com.dean.server.dto.ResultDTO;
import com.dean.server.entity.ExamEntity;
import com.dean.server.entity.UserEntity;
import com.dean.server.repository.ExamRepository;
import com.dean.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {
    Logger logger = LoggerFactory.getLogger(ExamService.class);

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ResultDTO> savePostExam(ExamPostDTO examPostDTO){
        List<String> msvList = userRepository.getAllMsv();
        ExamEntity examEntity = new ExamEntity();
        ResultDTO resultDTO = new ResultDTO();

        if(examPostDTO.getHiddenValue().isEmpty() == false){

            for(String msv: msvList){
                if(examPostDTO.getHiddenValue().contains(msv)){
                    examEntity.setCopyAuthor(userRepository.getUsernameByMsv(msv));
                }
            }
            examEntity.setExamSolution(examPostDTO.getExamSolution());
            examEntity.setExamValid((short) 0);
            examEntity.setAuthor(examPostDTO.getAuthor());
        } else  {
            examEntity.setExamSolution(examPostDTO.getExamSolution());
            examEntity.setExamValid((short) 1);
            examEntity.setAuthor(examPostDTO.getAuthor());
            examEntity.setCopyAuthor(null);
        }

        examRepository.save(examEntity);
        resultDTO.setErrorCode("0");
        resultDTO.setMessage("success");
        resultDTO.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }
}
