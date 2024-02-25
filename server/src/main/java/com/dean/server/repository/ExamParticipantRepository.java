package com.dean.server.repository;

import com.dean.server.entity.ExamParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamParticipantRepository extends JpaRepository<ExamParticipantEntity, Integer> {

    @Query("select ept from exam_participant ept " +
            "where ept.examProblem.id = ?1 and ept.student.username = ?2")
    ExamParticipantEntity getExamParticipantByExamIdAndUsername(Integer examId, String username);
}
