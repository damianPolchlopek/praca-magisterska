package com.polchlopek.praca.magisterska.DTO;

import com.polchlopek.praca.magisterska.entity.Measurement;

import java.util.Date;

public class MeasurementToTable {

    private String firstName;

    private String lastName;

    private Date date;

    private String category;

    private String description;

    private int id;


    public MeasurementToTable(Measurement measurement) {
        this.id = measurement.getId();
        this.firstName = measurement.getUserID().getFirstName();
        this.lastName = measurement.getUserID().getLastName();
        this.date = measurement.getDateMeasurement();
        this.category = measurement.getCategory().getCategory();
        this.description = measurement.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MeasurementToTable{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
