package com.dean.server.repository;

import com.dean.server.dto.ExamInformationDTO;
import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.entity.ExamProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamProblemRepository extends JpaRepository<ExamProblemEntity, Integer> {

    @Query("select epm.createBy, epm.examDescription, epm.examTitle, " +
            "epm.startTime, epm.endTime, ua.username, epm.duration, es.examSolution " +
            "from exam_problem epm " +
            "join exam_participant ept on epm.id = ept.examProblem.id " +
            "join user_account ua on ua.id = ept.student.id " +
            "left join exam_solution es on ept.id = es.examParticipantEntity.id ")
     List<List<Object>> getAllExamProblemWithStudent();
}
