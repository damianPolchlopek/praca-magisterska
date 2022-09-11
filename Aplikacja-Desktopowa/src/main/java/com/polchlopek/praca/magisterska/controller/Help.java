package com.polchlopek.praca.magisterska.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Help {

    @FXML
    private Label helpLabel;

    public void setHelpLabel(String helpLabel) {
        this.helpLabel.setText(helpLabel);
    }
}
