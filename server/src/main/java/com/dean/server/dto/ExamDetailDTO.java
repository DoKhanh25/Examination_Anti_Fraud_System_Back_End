package com.dean.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDetailDTO {
    Long examDuration;
    String examDescription;
    String examTitle;
    Long submitDuration;
    String examSolution;
    Boolean examDone;
}
