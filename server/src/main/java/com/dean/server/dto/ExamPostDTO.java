package com.dean.server.dto;

import lombok.Data;

@Data
public class ExamPostDTO {
    private String examSolution;
    private String hiddenValue;
    private String author;
}
