package com.dean.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "exam_solution")
    String examSolution;

    @Column(name = "author")
    String author;

    @Column(name = "exam_valid")
    Short examValid;

    @Column(name = "copy_author")
    String copyAuthor;




}
