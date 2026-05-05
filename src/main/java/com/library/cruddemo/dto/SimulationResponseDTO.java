package com.library.cruddemo.dto;

import java.time.LocalDate;
import java.util.List;

public class SimulationResponseDTO extends ResponseDTO {

    private String exhibition;

    private String library;

    private LocalDate simulationDate;

    private List<SimulationEntryDTO> ranking;

    public SimulationResponseDTO() {
    }

    public SimulationResponseDTO(Integer id, String exhibition, String library, LocalDate simulationDate, List<SimulationEntryDTO> ranking) {
        super(id);
        this.exhibition = exhibition;
        this.library = library;
        this.simulationDate = simulationDate;
        this.ranking = ranking;
    }

    public String getExhibition() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public LocalDate getSimulationDate() {
        return simulationDate;
    }

    public void setSimulationDate(LocalDate simulationDate) {
        this.simulationDate = simulationDate;
    }

    public List<SimulationEntryDTO> getRanking() {
        return ranking;
    }

    public void setRanking(List<SimulationEntryDTO> ranking) {
        this.ranking = ranking;
    }
}
