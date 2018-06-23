package com.polchlopek.praca.magisterska.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ImageView logoutImage;

    @FXML
    public void showTimeGraph() throws IOException {
        Node root_time = FXMLLoader.load(getClass().getClassLoader().getResource("com/polchlopek/magisterska/centerTime.fxml"));
        mainBorderPane.setCenter(root_time);
    }

    @FXML
    public void showFrequenceGraph() throws IOException {
        Node root_freq = FXMLLoader.load(getClass().getClassLoader().getResource("com/polchlopek/magisterska/centerFrequence.fxml"));
        mainBorderPane.setCenter(root_freq);
    }

    @FXML
    public void showNotes() throws IOException {
        Node root_notes = FXMLLoader.load(getClass().getClassLoader().getResource("com/polchlopek/magisterska/centerNotes.fxml"));
        mainBorderPane.setCenter(root_notes);
    }

    @FXML
    public void logout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginPanel.fxml"));
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
    public void showHelpTimeDialog(){
        System.out.println("Help Time");
    }

    @FXML
    public void showHelpDialog(){
        System.out.println("Help Dialog");
    }

    @FXML
    public void showAboutTimeDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Time Signal");
        dialog.setHeaderText("In this window you can see all important information about signal");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("helpDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        ControllerHelp helpController = fxmlLoader.getController();
        helpController.setHelpLabel("Pomoc okienkowa na temat pomiarow !!!!");
        dialog.showAndWait();
    }

    @FXML
    public void showAboutFrequenceDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Frequence Signal");
        dialog.setHeaderText("In this window you can see all important information about signal");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("helpDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        ControllerHelp helpController = fxmlLoader.getController();
        helpController.setHelpLabel("Frequence !!!!!!");
        dialog.showAndWait();
    }

    @FXML
    public void showAboutNoteDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Note");
        dialog.setHeaderText("In this window you can see all important information about note");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("helpDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        ControllerHelp helpController = fxmlLoader.getController();
        helpController.setHelpLabel("Pomoc okienkowa na temat dodawania notatek !!!!");
        dialog.showAndWait();
    }

}
