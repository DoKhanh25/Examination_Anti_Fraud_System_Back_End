package com.dean.server.repository;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.entity.ExamParticipantEntity;
import com.dean.server.entity.ExamSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExamSolutionRepository extends JpaRepository<ExamSolutionEntity, Integer>, ExamSolutionRepositoryCustom{

    ExamSolutionEntity getExamSolutionEntityByExamParticipantEntity(ExamParticipantEntity examParticipant);

    @Query("select es.examFinish from exam_solution es " +
            "where es.examParticipantEntity.student.username = :#{#dto.username} " +
            "and es.examParticipantEntity.examProblem.id = :#{#dto.examId}")
    Date getExamFininshByParticipantDTO(@Param("dto") ExamParticipantDTO examParticipantDTO);


}
