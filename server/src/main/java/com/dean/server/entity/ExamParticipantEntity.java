package com.dean.server.entity;

import com.dean.server.repository.ExamParticipantRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity(name = "exam_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    UserEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_problem_id")
    ExamProblemEntity examProblem;


}
