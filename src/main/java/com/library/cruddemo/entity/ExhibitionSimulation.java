package com.library.cruddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "exhibition_simulation")
public class ExhibitionSimulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "lib_id")
    private Lib lib;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "simulation_date")
    private LocalDate simulationDate;

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SimulationEntry> entries = new ArrayList<>();
    
    public ExhibitionSimulation() {
    }

    public ExhibitionSimulation(String title, Lib lib, LocalDate startDate, LocalDate endDate, LocalDate simulationDate) {
        this.title = title;
        this.lib = lib;
        this.startDate = startDate;
        this.endDate = endDate;
        this.simulationDate = simulationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Lib getLib() {
        return lib;
    }

    public void setLib(Lib lib) {
        this.lib = lib;
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

    public List<SimulationEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<SimulationEntry> entries) {
        this.entries = entries;
    }
}
