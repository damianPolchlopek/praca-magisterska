package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DTO.DescriptionFile;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DataToFile {

    @FXML
    private TextField descriptionMeasurementToFile;

    @FXML
    private TextField categoryMeasurementToFile;

    @FXML
    private TextField axisXMeasurementToFile;

    @FXML
    private TextField axisYMeasurementToFile;

    public void saveData(){
        DescriptionFile.getInstance().setDescription(descriptionMeasurementToFile.getText().trim());
        DescriptionFile.getInstance().setCategory(categoryMeasurementToFile.getText().trim());
        DescriptionFile.getInstance().setAxisX(axisXMeasurementToFile.getText().trim());
        DescriptionFile.getInstance().setAxisY(axisYMeasurementToFile.getText().trim());
    }


}
