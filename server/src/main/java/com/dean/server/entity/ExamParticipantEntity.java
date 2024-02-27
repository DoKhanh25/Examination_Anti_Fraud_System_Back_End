package com.dean.server.entity;

import com.dean.server.repository.ExamParticipantRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Entity(name = "exam_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamParticipantEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "student_id")
    UserEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "exam_problem_id")
    ExamProblemEntity examProblem;


}
