package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class UpdateUser {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;


    public void editUser(User user){
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        username.setText(user.getUsername());
        phone.setText(user.getPhone().toString());
    }

    public User updateUser(User user){
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setUsername(username.getText());
        user.setEmail(email.getText());
        user.setPhone(Integer.parseInt(phone.getText()));

        return user;
    }

}
