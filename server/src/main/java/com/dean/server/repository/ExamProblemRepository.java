package com.dean.server.repository;

import com.dean.server.entity.ExamProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamProblemRepository extends JpaRepository<ExamProblemEntity, Integer> {

}
