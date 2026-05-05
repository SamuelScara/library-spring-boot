package com.library.cruddemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class BookDTO extends ResponseDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @Positive(message = "Pubblication Year must be positive")
    private Integer pubYear;

    private boolean availability;

    private List<AuthorDTO> authors;

    public BookDTO() {
    }

    public BookDTO(Integer id, String title, String isbn, Integer pubYear, List<AuthorDTO> authors) {
        super(id);
        this.title = title;
        this.isbn = isbn;
        this.pubYear = pubYear;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPubYear() {
        return pubYear;
    }

    public void setPubYear(Integer pubYear) {
        this.pubYear = pubYear;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
}
