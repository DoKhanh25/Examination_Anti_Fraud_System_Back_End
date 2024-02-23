package com.dean.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

@Entity(name = "exam_problem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamProblemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(mappedBy = "examProblem")
    Set<ExamParticipantEntity> examParticipantEntitySet;

    @Column(name = "exam_title")
    String examTitle;

    @Column(name = "exam_description")
    String examDescription;

    @Column(name = "start_time")
    Date startTime;

    @Column(name = "end_time")
    Date endTime;

    @Column(name = "exam_duration")
    Duration duration;

    @Column(name = "created_by")
    String createBy;
}
