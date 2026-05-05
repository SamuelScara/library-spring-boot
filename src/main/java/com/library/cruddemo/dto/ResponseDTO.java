package com.library.cruddemo.dto;

public abstract class ResponseDTO {

    private Integer id;

    public ResponseDTO() {
    }

    public ResponseDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
