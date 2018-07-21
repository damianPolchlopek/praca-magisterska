package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.config.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainWindow {

    @FXML
    private BorderPane mainBorderPane;


    @FXML
    public void showTimeGraph() throws IOException {
        Node root_time = FXMLLoader.load(getClass().getResource("/view/centerTime.fxml"));
        mainBorderPane.setCenter(root_time);
    }

    @FXML
    public void showNotes() throws IOException {
        Node root_notes = FXMLLoader.load(getClass().getResource("/view/centerNotes.fxml"));
        mainBorderPane.setCenter(root_notes);
    }

    @FXML
    public void showSTMCommunication() throws IOException {
        Node root_notes = FXMLLoader.load(getClass().getResource("/view/communication.fxml"));
        mainBorderPane.setCenter(root_notes);
    }

    @FXML
    public void showDatabase() throws IOException {
        Node root_notes = FXMLLoader.load(getClass().getResource("/view/database.fxml"));
        mainBorderPane.setCenter(root_notes);
    }

    @FXML
    public void logout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPanel.fxml"));
        Main.getPrimaryStage().setScene(new Scene(root,900, 600));
    }

    @FXML
    public void saveDiagrams() {
        System.out.println("Zapisuje");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Signal");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All File", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );

        File file = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());

        if(file != null){
            try {
                saveFile("123456", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void saveFile(String content, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }

    @FXML
    public void openDiagrams(){
        System.out.println("Open");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All File", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );

        File selectedFile = fileChooser.showOpenDialog(mainBorderPane.getScene().getWindow());

        try {
            openFile(selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        String regex = "(\\d+) (\\d+)";
        Matcher allNumber;
        Pattern p = Pattern.compile(regex);

        while ((line = reader.readLine()) != null) {
            allNumber = p.matcher(line);
            if (allNumber.find()){
                System.out.print("czas: " + allNumber.group(1));
                System.out.println(", amplit: " + allNumber.group(2));
            }
        }
    }

    @FXML
    public void showAboutTimeDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Signal");
        dialog.setHeaderText("In this window you can see all important information about signal");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("W aplikacji zostały dodane dwa typy wykresów: liniowy i słupkowy. \n" +
                "Po zmianie odpowiedzniej zakładki w widoku odpowiedzialnym a wyświetlanie wykresów \n" +
                "ukazują się wykresy wyświetlające ostatnio otrzymane pomiary z mikrokontrolera.");
        dialog.showAndWait();
    }

    @FXML
    public void showDatabaseDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Database");
        dialog.setHeaderText("In this window you can see all important information about database");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("Za pomocą bazy danych użytkownik może zarządzać wybranymi przez siebie pomiarami. \n" +
                "------------------------------------------------------------------------------- \n" +
                "Dostępne operacje na bazie danych to: \n" +
                "1. Wyświetlanie i usuwanie danych o zarejestrowanych użytkownikach. \n" +
                "2. Wyświetlanie danych o logowaniach uzytkowników do aplikacji. \n" +
                "3. Wyświetlanie, dodawanie i usuwanie pomiarow. \n" +
                "4. Wyświetlanie, dodawnaie i usuwanie kategorii pomiarow. \n");
        dialog.showAndWait();
    }

    @FXML
    public void showCommunicationDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Communication");
        dialog.setHeaderText("In this window you can see all important information about communication between apllication and STM32");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("W widoku odpowiezialnym za kommunikacje z mikrokontrolerem mamy możliwość: \n" +
                "1. Kommunikacji z mikro kontrolerem. \n" +
                "2. Wybrania algorytmu, jaki chcemy użyć do przetworzenia, zmierzonego sygnału. \n" +
                "3. Ustawienia liczby próbek jakie chcemy zmierzyć. \n" +
                "4. Rozpoczęcia sekwencji pomiarowej.");
        dialog.showAndWait();
    }


    @FXML
    public void showAboutNoteDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Note");
        dialog.setHeaderText("In this window you can see all important information about note");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("W aplikacji jest możliwość dodawania, edytowania i usuwania notatek.");
        dialog.showAndWait();
    }

    private boolean loadContentToScreen(Dialog<ButtonType> dialog, FXMLLoader fxmlLoader) {
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return true;
        }
        return false;
    }

}
