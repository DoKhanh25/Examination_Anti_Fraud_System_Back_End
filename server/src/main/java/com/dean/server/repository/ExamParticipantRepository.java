package com.dean.server.repository;

import com.dean.server.entity.ExamParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamParticipantRepository extends JpaRepository<ExamParticipantEntity, Integer> {
}
