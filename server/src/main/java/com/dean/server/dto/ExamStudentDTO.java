package com.dean.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamStudentDTO {
    Integer id;
    String username;
    String msv;
    Date startTime;
    Date endTime;
    Long duration;
    String createBy;
    String examTitle;
    Long submitDuration;
    Boolean examDone;
    Float grade;
}
