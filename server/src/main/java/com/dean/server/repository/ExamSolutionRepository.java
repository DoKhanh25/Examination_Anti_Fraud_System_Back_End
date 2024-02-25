package com.dean.server.repository;

import com.dean.server.entity.ExamParticipantEntity;
import com.dean.server.entity.ExamSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamSolutionRepository extends JpaRepository<ExamSolutionEntity, Integer>{


    ExamSolutionEntity getExamSolutionEntityByExamParticipantEntity(ExamParticipantEntity examParticipant);

    @Query("select es from exam_solution es where es.examParticipantEntity.id = ?1")
    ExamSolutionEntity getExamSolutionEntityByParticipantId(Integer id);
}
