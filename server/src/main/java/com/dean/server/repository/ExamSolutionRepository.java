package com.dean.server.repository;

import com.dean.server.entity.ExamSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSolutionRepository extends JpaRepository<ExamSolutionEntity, Integer>{

}
