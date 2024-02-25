package com.dean.server.dto;

import com.dean.server.entity.ExamParticipantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import java.util.Date;

public class ExamSolutionDTO {
    String examSolution;
    String hiddenValue;
    Date submitTime;
    Long submitDuration;
    String username;
    Integer examId;

}
