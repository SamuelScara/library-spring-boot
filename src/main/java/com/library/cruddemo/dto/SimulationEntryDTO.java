package com.library.cruddemo.dto;

public class SimulationEntryDTO {

    private int position;

    private String bookTitle;

    private String authorName;

    private int visitorsNum;

    public SimulationEntryDTO() {
    }

    public SimulationEntryDTO(int position, String bookTitle, String authorName, int visitorsNum) {
        this.position = position;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.visitorsNum = visitorsNum;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getVisitorsNum() {
        return visitorsNum;
    }

    public void setVisitorsNum(int visitorsNum) {
        this.visitorsNum = visitorsNum;
    }
}
