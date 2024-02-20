package com.dean.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private String errorCode;
    private List<Object> listData;
    private Object data;
    private String message;

}
