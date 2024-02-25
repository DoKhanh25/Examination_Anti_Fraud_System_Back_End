package com.dean.server.dto;

import lombok.Data;

@Data
public class ExamDetailDTO {
    Long examDuration;
    String examDescription;
    String examTitle;
    Long submitDuration;
    String examSolution;
}
