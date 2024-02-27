package com.dean.server.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSolutionReponseDTO {
    String examSolution;

    short examValid;

    String solutionOriginal;

    Float grade;

    Date submitTime;

    Long submitDuration;

    short examDone;
}
