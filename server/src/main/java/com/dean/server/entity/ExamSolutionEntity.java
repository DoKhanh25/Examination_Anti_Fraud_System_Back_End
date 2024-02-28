package com.dean.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity(name = "exam_solution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamSolutionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "exam_solution", columnDefinition = "longtext")
    String examSolution;

    @Column(name = "exam_valid")
    short examValid;

    @JsonIgnore
    @OneToMany
    @Column(name = "exam_fraud")
    Set<UserEntity> examFraudEntitySet;

    @Column(name = "exam_grade")
    Float grade;

    @Column(name = "submit_time")
    Date submitTime;

    @Column(name = "submit_duration")
    Long submitDuration;

    @Column(name = "exam_done")
    Boolean examDone;

    @Column(name = "exam_finish")
    Date examFinish;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "exam_participant_id")
    ExamParticipantEntity examParticipantEntity;
}
