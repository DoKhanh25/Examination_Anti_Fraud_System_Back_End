package com.dean.server.repository;

import java.util.Date;

public interface ExamSolutionRepositoryCustom {

    public Integer insertExamFinishByParticipantId(Integer id, Date examFinish);
}
