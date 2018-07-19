package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DAO.LoginDAO;
import com.polchlopek.praca.magisterska.DAO.MeasurementCategoryDAO;
import com.polchlopek.praca.magisterska.DAO.MeasurementDAO;
import com.polchlopek.praca.magisterska.DAO.PersonDAO;
import com.polchlopek.praca.magisterska.DTO.LoginToTable;
import com.polchlopek.praca.magisterska.DTO.MeasurementToTable;
import com.polchlopek.praca.magisterska.DTO.ReceivedDataFromSTM;
import com.polchlopek.praca.magisterska.entity.*;
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


    // DODAWANIE POMIARU
    @FXML
    private TextField descriptionMeasurementToAdd;

    @FXML
    private ComboBox measCategoryInMeasToAdd;

    // DODANIE KATEOGRII POMIARU
    @FXML
    private TextField descriptionMeasurementCategoryToAdd;

    @FXML
    private TextField axisXMeasurementCategoryToAdd;

    @FXML
    private TextField axisYMeasurementCategoryToAdd;

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

        // wyswietlanie measurement category w zakaldce
        // odpowiedzialnej za dodawnie pomiarow
        List<String> measurementCategories = new MeasurementCategoryDAO().getMeasurementCategories();
        ObservableList<String> measCategory = FXCollections.observableArrayList(measurementCategories);
        measCategoryInMeasToAdd.setItems(measCategory);
        measCategoryInMeasToAdd.getSelectionModel().selectFirst();

    }


    @FXML
    public void addMeasurementCategoryToDatabase(){

        String description = descriptionMeasurementCategoryToAdd.getText().trim();
        String axisX = axisXMeasurementCategoryToAdd.getText().trim();
        String axisY = axisYMeasurementCategoryToAdd.getText().trim();

        MeasurementCategory measurementCategory = new MeasurementCategory(description, axisX, axisY);
        MeasurementCategoryDAO measurementCategoryDAO = new MeasurementCategoryDAO();
        measurementCategoryDAO.addMeasurementCategory(measurementCategory);

    }

    @FXML
    public void addMeasurementToDatabase(){

        String description = descriptionMeasurementToAdd.getText().trim();
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utilDate.getTime());

        MeasurementCategoryDAO measurementCategoryDAO = new MeasurementCategoryDAO();
        //MeasurementCategory measurementCategory = measurementCategoryDAO.getMeasurementCategory("Temperatura");
        String measCat = (String) measCategoryInMeasToAdd.getValue();
        MeasurementCategory measurementCategory = measurementCategoryDAO.getMeasurementCategory(measCat);

        System.out.println("Dodanie pomiaru do bazy danych");
        System.out.println("Meas category: " + measurementCategory);

        PersonDAO personDAO = new PersonDAO();
        User user = personDAO.getPerson("damian");

        // TODO: ODKOMENTOWAC GDY BEDZIe GOTOWA APLIKACJA
//        User user = LoggedInUser.getInstance().getLoggedInUSer();

        //mrasurement data
        List<MeasurementData> listMeasurementData = new ArrayList<>();
        for (int i = 0; i < ReceivedDataFromSTM.getInstance().getList().size(); ++i) {
            listMeasurementData.add(new MeasurementData(i,
                    ReceivedDataFromSTM.getInstance().getList().get(i)));
        }

        System.out.println("list: " + listMeasurementData);

        Measurement measurement = new Measurement(date, description, measurementCategory,
                                                        user, listMeasurementData);

        MeasurementDAO measurementDAO = new MeasurementDAO();
        measurementDAO.addMeasurement(measurement);

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
        List<MeasurementToTable> measurementsDB = new ArrayList<>();

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
        List<User> users = per.getPeople();

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
        List<LoginToTable> loginsDB = new ArrayList<>();

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
