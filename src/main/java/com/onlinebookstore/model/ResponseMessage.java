package com.onlinebookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    public ResponseMessage(Integer statusCode, String status, String message, Object data) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private Integer statusCode;
    private String status;
    private String message;
    private Object data;
    private List<?> list;
}
