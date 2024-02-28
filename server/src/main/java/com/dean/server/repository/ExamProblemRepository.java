package com.dean.server.repository;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.entity.ExamEntity;
import com.dean.server.entity.ExamProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamProblemRepository extends JpaRepository<ExamProblemEntity, Integer> {

    @Query("select epm.createBy, epm.examDescription, epm.examTitle, " +
            "epm.startTime, epm.endTime, ua.username, epm.duration, es.examSolution, epm.id " +
            "from exam_problem epm " +
            "join exam_participant ept on epm.id = ept.examProblem.id " +
            "join user_account ua on ua.id = ept.student.id " +
            "left join exam_solution es on ept.id = es.examParticipantEntity.id ")
     List<List<Object>> getAllExamProblemWithStudent();

    @Query("select ep.id, ua.username, ua.msv, ep.startTime, " +
            "ep.endTime, ep.duration, ep.createBy, ep.examTitle, es.examDone " +
            "from user_account ua " +
            "join exam_participant ept on ua.id = ept.student.id " +
            "join exam_problem ep on ept.examProblem.id = ep.id " +
            "join exam_solution es on ept.id = es.examParticipantEntity.id " +
            "where ua.username = ?1")
    List<List<Object>> getExamProblemByUsername(String username);

    @Query("select ep.duration, ep.examDescription, ep.examTitle, es.submitDuration, es.examSolution, es.examDone " +
            "from exam_problem ep " +
            "join exam_participant ept on ep.id = ept.examProblem.id " +
            "join exam_solution es on es.examParticipantEntity.id = ept.id " +
            "where ept.examProblem.id = :#{#dto.examId} and ept.student.username = :#{#dto.username}")
    List<List<Object>> getExamDetailByExamParticipant(@Param("dto") ExamParticipantDTO examParticipantDTO);

    @Query("select ep.duration from exam_problem ep where ep.id = ?1")
    Long getExamProblemDurationById(Integer id);

    ExamProblemEntity getExamProblemEntityById(Integer id);




}
