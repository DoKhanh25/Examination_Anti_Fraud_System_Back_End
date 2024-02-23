package com.dean.server.dto;

import com.dean.server.entity.ExamParticipantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamProblemDTO {
     List<String> examParticipantSet;

    String examTitle;

    String examDescription;

    Date startTime;

    Date endTime;

    Long duration;

    String createBy;
}
