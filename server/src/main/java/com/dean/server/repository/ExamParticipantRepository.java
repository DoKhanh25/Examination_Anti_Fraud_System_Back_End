package com.dean.server.repository;

import com.dean.server.dto.ExamParticipantDTO;
import com.dean.server.entity.ExamParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamParticipantRepository extends JpaRepository<ExamParticipantEntity, Integer> {



    @Query("select ept from exam_participant ept where ept.student.username = :#{#dto.username} and ept.examProblem.id = :#{#dto.examId}")
    ExamParticipantEntity getExamParticipantEntityByExamParticipantDTO(@Param("dto") ExamParticipantDTO examParticipantDTO);
}
