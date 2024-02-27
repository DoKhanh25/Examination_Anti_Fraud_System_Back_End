package com.dean.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSolutionRequestDTO {
    String examSolution;
    String hiddenValue;
    Date submitTime;
    Long submitDuration;
    String username;
    Integer examId;
    Boolean examDone;

}
