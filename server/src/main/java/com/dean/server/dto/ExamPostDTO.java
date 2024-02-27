package com.dean.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamPostDTO {
    private String examSolution;
    private String hiddenValue;
    private String author;
}
