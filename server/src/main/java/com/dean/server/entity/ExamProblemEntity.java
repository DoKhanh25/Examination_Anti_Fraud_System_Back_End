package com.dean.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

@Entity(name = "exam_problem")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamProblemEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @JsonIgnore
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
    Long duration;

    @Column(name = "created_by")
    String createBy;
}
