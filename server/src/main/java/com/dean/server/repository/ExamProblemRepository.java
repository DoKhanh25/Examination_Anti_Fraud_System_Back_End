package com.dean.server.repository;

import com.dean.server.dto.ExamInformationDTO;
import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.dto.ExamProblemDTO;
import com.dean.server.entity.ExamProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Duration;
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

    @Query("select ep.id, ua.username, ua.msv, ep.startTime, " +
            "ep.endTime, ep.duration, ep.createBy, ep.examTitle " +
            "from user_account ua " +
            "join exam_participant ept on ua.id = ept.student.id " +
            "join exam_problem ep on ept.examProblem.id = ep.id " +
            "where ua.username = ?1")
    List<List<Object>> getExamProblemByUsername(String username);

    @Query("select ep.duration, ep.examDescription, ep.examTitle, es.submitDuration, es.examSolution " +
            "from exam_problem ep " +
            "join exam_participant ept on ep.id = ept.examProblem.id " +
            "join exam_solution es on es.examParticipantEntity = ept " +
            "where ept.examProblem.id = :#{#dto.examId} and ept.student.username = :#{#dto.username}")
    List<Object> getExamDetailByExamParticipant(@Param("dto") ExamParticipantDTO examParticipantDTO);


}
