package com.dean.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamSolutionDetailDTO {
    String examSolution;
    short examValid;
    Set<String> examFraud;
    Float grade;
    Date submitTime;
    Long submitDuration;
    Boolean examDone;
    String examTitle;
    String examDescription;
    Long duration;
}
