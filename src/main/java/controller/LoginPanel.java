package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPanel {


    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label checkCorrectPassLabel;


    @FXML
    public void checkLogin(ActionEvent event) throws IOException {
        if(loginTextField.getText().trim().equals("") && passwordField.getText().trim().equals("")){
            Parent mainWindow = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
            Scene mainWindowScene = new Scene(mainWindow);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainWindowScene);
            window.show();
        }
        else{
            checkCorrectPassLabel.setText("Incorrect pass or login !!!");
        }

    }


}
