package com.polchlopek.praca.magisterska.DTO;

import com.polchlopek.praca.magisterska.entity.Login;

import java.sql.Time;
import java.util.Date;

public class LoginToTable {


    private String firstName;

    private String lastName;

    private Date date;

    private Time time;

    private String location;

    public LoginToTable(Login login) {
        this.firstName = login.getUserID().getFirstName();
        this.lastName = login.getUserID().getLastName();
        this.date = login.getDateLog();
        this.time = login.getTimeLog();
        this.location = login.getLocationLog();
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "LoginToTable{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", location='" + location + '\'' +
                '}';
    }
}
