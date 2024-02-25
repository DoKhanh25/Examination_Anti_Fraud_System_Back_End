package com.dean.server.dto;

import lombok.Data;

import java.util.Date;

@Data
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
}
