package com.dean.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "exam_solution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamSolutionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "exam_solution")
    String examSolution;

    @Column(name = "exam_valid")
    short examValid;

    @Column(name = "solution_original_author")
    String solutionOriginal;

    @Column(name = "exam_grade")
    Float grade;

    @Column(name = "submit_time")
    Date submitTime;

    @Column(name = "submitDuration")
    Long submitDuration;

    @OneToOne
    @MapsId
    @JoinColumn(name = "exam_participant_id")
    private ExamParticipantEntity examParticipantEntity;
}
