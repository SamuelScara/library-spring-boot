package com.library.cruddemo.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class LibDTO extends ResponseDTO {

    @NotBlank(message = "Library name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    private DirectorDTO director;

    private List<BookDTO> books;

    public LibDTO() {
    }

    public LibDTO(Integer id, String name, String city, String address, DirectorDTO director, List<BookDTO> books) {
        super(id);
        this.name = name;
        this.city = city;
        this.address = address;
        this.director = director;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DirectorDTO getDirector() {
        return director;
    }

    public void setDirector(DirectorDTO director) {
        this.director = director;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
