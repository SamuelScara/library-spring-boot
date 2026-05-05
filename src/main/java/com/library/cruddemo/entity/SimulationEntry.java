package com.library.cruddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Table
@Entity(name = "simulation_entry")
public class SimulationEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "simulation_id")
    private ExhibitionSimulation simulation;

    @Column(name = "position")
    private int position;

    @Column(name = "visitors_num")
    private int visitorsNum;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public SimulationEntry() {
    }

    public SimulationEntry(ExhibitionSimulation simulation, int position, int visitorsNum, Book book) {
        this.simulation = simulation;
        this.position = position;
        this.visitorsNum = visitorsNum;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExhibitionSimulation getSimulation() {
        return simulation;
    }

    public void setSimulation(ExhibitionSimulation simulation) {
        this.simulation = simulation;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getVisitorsNum() {
        return visitorsNum;
    }

    public void setVisitorsNum(int visitorsNum) {
        this.visitorsNum = visitorsNum;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
