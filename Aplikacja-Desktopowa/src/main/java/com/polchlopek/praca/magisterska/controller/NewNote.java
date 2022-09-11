package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DTO.notedata.*;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.time.LocalDate;

public class NewNote {

    @FXML
    private TextField headingField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField authorField;

    @FXML
    private DatePicker deadlinePicker;

    private String trimmer(TextInputControl myTextFieldToTrim){
        return myTextFieldToTrim.getText().trim();
    }

    public NoteItem processResults(){
        String heading = trimmer(headingField);
        String description = trimmer(descriptionField);
        String author = trimmer(authorField);
        LocalDate deadlineValue = deadlinePicker.getValue();
        NoteItem newItem = new NoteItem(heading, description, author, deadlineValue);
        NoteData.getInstance().addNoteItem(newItem);
        return newItem;
    }

    public void editContact(NoteItem item) {
        headingField.setText(item.getHeading());
        descriptionField.setText(item.getDescription());
        authorField.setText(item.getAuthor());
        deadlinePicker.setValue(item.getDeadline());
    }

    public void updateContact(NoteItem item) {
        item.setHeading(headingField.getText());
        item.setDescription(descriptionField.getText());
        item.setDeadline(deadlinePicker.getValue());
        item.setAuthor(authorField.getText());
    }

}
