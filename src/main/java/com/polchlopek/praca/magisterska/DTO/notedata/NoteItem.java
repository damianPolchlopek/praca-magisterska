package com.polchlopek.praca.magisterska.DTO.notedata;

import java.time.LocalDate;

public class NoteItem {

    private String heading;
    private String description;
    private String author;
    private LocalDate deadline;

    public NoteItem(String heading, String description, String author, LocalDate deadline) {
        this.heading = heading;
        this.description = description;
        this.author = author;
        this.deadline = deadline;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "NoteItem{" +
                "heading='" + heading + '\'' +
                '}';
    }
}
