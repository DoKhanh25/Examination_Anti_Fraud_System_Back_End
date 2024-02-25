package com.dean.server.dto;

import lombok.Data;

@Data
public class ExamSocketDTO {
    String examSolution;
    Long duration;
    String sender;
    Integer examId;
}
