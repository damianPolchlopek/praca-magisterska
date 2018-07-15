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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @FXML
    private void search(){

//        MeasurementDAO meas = new MeasurementDAO();
//        measurements = meas.getMeasurements();
//
//        ObservableList<Measurement> obserList = FXCollections.observableArrayList(measurements);
//        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateMeasurement"));
//        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
//        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
//        measurementTable.setItems(obserList);
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


}
