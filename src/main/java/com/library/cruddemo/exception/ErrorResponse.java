package com.library.cruddemo.exception;

public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private long timestamp;


    public ErrorResponse(int status, String message, String code, long timestamp) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public long getTimestamp() {
        return timestamp;
    }
}