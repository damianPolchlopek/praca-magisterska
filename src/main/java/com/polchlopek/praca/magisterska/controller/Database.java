package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DAO.LoginDAO;
import com.polchlopek.praca.magisterska.DAO.MeasurementDAO;
import com.polchlopek.praca.magisterska.DAO.PersonDAO;
import com.polchlopek.praca.magisterska.DTO.LoginToTable;
import com.polchlopek.praca.magisterska.DTO.MeasurementToTable;
import com.polchlopek.praca.magisterska.entity.Login;
import com.polchlopek.praca.magisterska.entity.Measurement;
import com.polchlopek.praca.magisterska.entity.MeasurementCategory;
import com.polchlopek.praca.magisterska.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Database {

    // TABELA WYSWIETLAJACA POMIARY
    @FXML
    private TableView<MeasurementToTable> measurementTable;

    @FXML
    private TableColumn<String, String> firstNameCol;

    @FXML
    private TableColumn<String, String> lastNameCol;

    @FXML
    private TableColumn<Date, String> dateCol;

    @FXML
    private TableColumn<String, String> descriptionCol;

    @FXML
    private TableColumn<MeasurementCategory, String> categoryCol;

    @FXML
    private Button btnSearch;


    // TABELA WYSWIETLAJACA UZYTKOWNIKOW
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<String, String> firstNameUserCol;

    @FXML
    private TableColumn<String, String> lastNameUserCol;

    @FXML
    private TableColumn<String, String> emailUserCol;

    @FXML
    private TableColumn<Integer, String> phoneUserCol;


    // TABELA WYSWIETLAJACA LOGINY
    @FXML
    private TableView<LoginToTable> loginTable;

    @FXML
    private TableColumn<String, String> firstNameLoginCol;

    @FXML
    private TableColumn<String, String> lastNameLoginCol;

    @FXML
    private TableColumn<Date, String> dateLoginCol;

    @FXML
    private TableColumn<Time, String> timeLoginCol;

    @FXML
    private TableColumn<String, String> locationLoginCol;

    private List<MeasurementToTable> measurementsDB;
    private List<LoginToTable> loginsDB;
    private List<User> users;


    // DODAWANIE NOWEGO UZYTKOWNIKA
    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField nicknameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField password2TextField;

    @FXML
    private TextField phoneTextField;


    // MENU KONTEKSTOWE
    @FXML
    private MenuItem measurementDeleteMenuItem;

    @FXML
    private MenuItem userDeleteMenuItem;

    @FXML
    private MenuItem loginDeleteMenuItem;


    @FXML
    private BorderPane mainPanel;



    public void initialize(){

        measurementDeleteMenuItem.setOnAction(event -> {
            MeasurementToTable measFromTable = measurementTable.getSelectionModel().getSelectedItem();
            Measurement measurement = new Measurement(measFromTable);
            MeasurementDAO measDAO = new MeasurementDAO();
            measDAO.deleteMeasurement(measurement);
//            initMeasurementTab();
        });

        userDeleteMenuItem.setOnAction(event -> {
            User user = userTable.getSelectionModel().getSelectedItem();
            PersonDAO perDAO = new PersonDAO();
            perDAO.deletePerson(user);
            initUserTab();
        });

        loginDeleteMenuItem.setOnAction(event -> {
            LoginToTable loginToTable = loginTable.getSelectionModel().getSelectedItem();
            LoginDAO logDAO = new LoginDAO();
            Login login = logDAO.getLogin(loginToTable.getId());
            logDAO.deleteLogin(login);
            initLoginTab();
        });
    }


    @FXML
    public void updateUserMenuItem(){
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit User");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/updateUser.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e){
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        UpdateUser userController = fxmlLoader.getController();
        userController.editUser(selectedUser);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            User user = userController.updateUser(selectedUser);
            PersonDAO perDAO = new PersonDAO();
            perDAO.savePerson(user);
            initUserTab();
        }

    }


    @FXML
    private void initMeasurementTab(){

        MeasurementDAO meas = new MeasurementDAO();
        List<Measurement> measurements = meas.getMeasurements();
        measurementsDB = new ArrayList<>();

        for (Measurement measurement :
                measurements) {
            measurementsDB.add(new MeasurementToTable(measurement));
        }

        ObservableList<MeasurementToTable> obserList = FXCollections.observableArrayList(measurementsDB);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        measurementTable.setItems(obserList);
    }

    @FXML
    private void initUserTab(){
        PersonDAO per = new PersonDAO();
        users = per.getPeople();

        ObservableList<User> obserList = FXCollections.observableArrayList(users);
        firstNameUserCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameUserCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailUserCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneUserCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userTable.setItems(obserList);
    }

    @FXML
    private void initLoginTab(){

        LoginDAO log = new LoginDAO();
        List<Login> logins = log.getLogins();
        loginsDB = new ArrayList<>();

        for (Login login : logins) {
            loginsDB.add(new LoginToTable(login));
        }

        ObservableList<LoginToTable> obserList = FXCollections.observableArrayList(loginsDB);
        firstNameLoginCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameLoginCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateLoginCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeLoginCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        locationLoginCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        loginTable.setItems(obserList);
    }

    @FXML
    private void addUser(){
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        Integer phone = Integer.parseInt(phoneTextField.getText().trim());
        String password = passwordTextField.getText().trim();
        String password2 = password2TextField.getText().trim();
        String username = nicknameTextField.getText().trim();

        // dorobic sprawdzanie poprawsci danych
        // np. czy 2 wprowadzone hasla sie zgadzaja


        User userToAdd = new User(username, firstName, lastName, email, password, phone);
        PersonDAO per = new PersonDAO();
        per.savePerson(userToAdd);

        initUserTab();
    }



}
