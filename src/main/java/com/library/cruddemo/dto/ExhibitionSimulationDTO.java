package com.library.cruddemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class ExhibitionSimulationDTO extends ResponseDTO {

    @NotBlank(message = "Title of the Exhibition is required")
    private String title;

    @NotNull(message = "Starting date required")
    private LocalDate startDate;

    @NotNull(message = "Ending date required")
    private LocalDate endDate;

    @NotNull(message = "Simulation date required")
    private LocalDate simulationDate;

    private Integer libId;

    private List<Integer> bookIds;

    public ExhibitionSimulationDTO() {
    }

    public ExhibitionSimulationDTO(Integer id, String title, LocalDate startDate, LocalDate endDate, LocalDate simulationDate, Integer libId) {
        super(id);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.simulationDate = simulationDate;
        this.libId = libId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getSimulationDate() {
        return simulationDate;
    }

    public void setSimulationDate(LocalDate simulationDate) {
        this.simulationDate = simulationDate;
    }

    public Integer getLibId() {
        return libId;
    }

    public void setLibId(Integer libId) {
        this.libId = libId;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }
}
