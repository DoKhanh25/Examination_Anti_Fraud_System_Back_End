package com.dean.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "exam_participant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    UserEntity student;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    Course course;


}
