package com.polchlopek.praca.magisterska.DTO;

import com.polchlopek.praca.magisterska.entity.Measurement;

public class ReceivedDataFromFile {

    private static ReceivedDataFromFile ourInstance = new ReceivedDataFromFile();

    public static ReceivedDataFromFile getInstance() {
        return ourInstance;
    }

    private Measurement measurement;

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public void clearData(){
        measurement = null;
    }

}
