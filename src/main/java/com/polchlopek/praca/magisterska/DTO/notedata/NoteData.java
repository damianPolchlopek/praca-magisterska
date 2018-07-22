package com.polchlopek.praca.magisterska.DTO.notedata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NoteData {

    private static NoteData instance = new NoteData();
    private final static String filename = "NoteListItems.txt";
    private final static String DATEPATTERN = "dd-MM-yyyy";
    private ObservableList<NoteItem> noteItems;
    private DateTimeFormatter formatter;

    public static NoteData getInstance() {
        return instance;
    }

    private NoteData(){
        formatter = DateTimeFormatter.ofPattern(DATEPATTERN);
    }

    public ObservableList<NoteItem> getNoteItems(){
        return noteItems;
    }

    public void loadNoteItems() throws IOException {
        noteItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);

        String input;

        try (BufferedReader myBufferedReader = Files.newBufferedReader(path)) {
            while ((input = myBufferedReader.readLine()) != null) {
                String[] itemPieces = input.split("\t");
                int i = 0;
                String heading = itemPieces[i];
                String description = itemPieces[++i];
                String author = itemPieces[++i];
                String dateString = itemPieces[++i];

                LocalDate date = LocalDate.parse(dateString, formatter);
                NoteItem noteItem = new NoteItem(heading, description, author, date);

                noteItems.add(noteItem);
            }
        }
    }

    public void storeNoteItems() throws IOException {
        Path path = Paths.get(filename);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (NoteItem item : noteItems) {
                bw.write(String.format("%s\t%s\t%s\t%s",
                        item.getHeading(),
                        item.getDescription(),
                        item.getAuthor(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }

        }
    }

    public void addNoteItem(NoteItem note){
        noteItems.add(note);
    }

    public void deleteNoteItem(NoteItem note){
        noteItems.remove(note);
    }

}
