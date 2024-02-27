package com.dean.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Date;

@Transactional
public class ExamSolutionRepositoryImpl implements ExamSolutionRepositoryCustom {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public Integer insertExamFinishByParticipantId(Integer id, Date examFinish){
        String sqlQuery = "UPDATE exam_solution set exam_finish = :exam_finish where exam_participant_id = :id";

        return  em.createNativeQuery(sqlQuery)
                .setParameter("exam_finish", examFinish)
                .setParameter("id", id)
                .executeUpdate();
    }
}
