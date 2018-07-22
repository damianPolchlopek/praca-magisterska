package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DTO.notedata.NoteItem;
import com.polchlopek.praca.magisterska.DTO.notedata.NoteData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

public class Notes {

    @FXML
    private BorderPane noteBorderPane;

    @FXML
    private ListView<NoteItem> noteListView;

    @FXML
    private Label deadlineLabel;

    @FXML
    private TextArea noteDescriptionTextArea;

    @FXML
    private Label authorLabel;

    @FXML
    private ContextMenu listContextMenu;

    // sortowanie list View
    SortedList<NoteItem> sortedList = new SortedList<NoteItem>(NoteData.getInstance().getNoteItems(),
            new Comparator<NoteItem>() {
                @Override
                public int compare(NoteItem o1, NoteItem o2) {
                    return o1.getDeadline().compareTo(o2.getDeadline());
                }
            });

    public void initialize(){

        // stworzenie menu kontekstowego
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NoteItem item = noteListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);


        // listener czekajacy na klikniecie naglowka i ustawiajacy opis
        noteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NoteItem>() {
            @Override
            public void changed(ObservableValue<? extends NoteItem> observable, NoteItem oldValue, NoteItem newValue) {
                if(newValue != null){
                    NoteItem item = noteListView.getSelectionModel().getSelectedItem();
                    noteDescriptionTextArea.setText(item.getDescription());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
                    authorLabel.setText(item.getAuthor());
                }
            }
        });

        noteListView.setItems(sortedList);
        noteListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        noteListView.getSelectionModel().selectFirst();

        noteListView.setCellFactory(new Callback<ListView<NoteItem>, ListCell<NoteItem>>() {
            @Override
            public ListCell<NoteItem> call(ListView<NoteItem> param) {

                ListCell<NoteItem> cell = new ListCell<NoteItem>(){
                    @Override
                    protected void updateItem(NoteItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }
                        else{
                            setText(item.getHeading());
                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))){
                                setTextFill(Color.RED);
                            }
                            else if(item.getDeadline().equals(LocalDate.now().plusDays(1))){
                                setTextFill(Color.DARKORANGE);
                            }
                        }
                    }
                };

                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if(isNowEmpty){
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });


                return cell;
            }
        });

    }



    @FXML
    public void showNewNoteDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(noteBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Note");
        dialog.setHeaderText("Use this dialog to create a new note item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/noteItemDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            NewNote controller = fxmlLoader.getController();
            NoteItem newItem = controller.processResults();
            noteListView.getSelectionModel().select(newItem);
        }
    }

    private boolean loadDialog(Dialog<ButtonType> dialog, FXMLLoader fxmlLoader) {
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @FXML
    public void showEditNoteDialog(){
        NoteItem selectedContact = noteListView.getSelectionModel().getSelectedItem();
        if(selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(noteBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Note");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/noteItemDialog.fxml"));

        if (loadDialog(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        NewNote contactController = fxmlLoader.getController();
        contactController.editContact(selectedContact);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            contactController.updateContact(selectedContact);
            noteListView.setItems(null);
            noteListView.setItems(sortedList);
            noteListView.getSelectionModel().select(selectedContact);
        }
    }

    @FXML
    public void deleteNote(){
        NoteItem item = noteListView.getSelectionModel().getSelectedItem();
        deleteItem(item);
    }

    public void deleteItem(NoteItem item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Note Item");
        alert.setHeaderText("Delete note: " + item.getDescription());
        alert.setContentText("Are you sure? Press OK to confirm.");
        Optional<ButtonType> result = alert.showAndWait();
//        if(result.orElse(ButtonType.NO) == ButtonType.OK) {
//            NoteData.getInstance().deleteNoteItem(item);
//        }

        if (result.isPresent() && (result.get() == ButtonType.OK)){
            NoteData.getInstance().deleteNoteItem(item);
        }
    }
}
