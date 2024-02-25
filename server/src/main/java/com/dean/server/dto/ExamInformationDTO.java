package com.dean.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamInformationDTO {
    String createBy;
    String examDescription;
    String examTitle;
    Date startTime;
    Date endTime;
    String username;
    Long duration;
    String examSolution;
    Integer examId;


}
