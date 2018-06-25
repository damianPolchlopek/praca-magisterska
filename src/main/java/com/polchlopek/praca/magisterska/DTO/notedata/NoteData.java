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
import java.util.Iterator;

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
        BufferedReader myBufferedReader = Files.newBufferedReader(path);

        String input;

        try{
            while((input = myBufferedReader.readLine()) != null){
                String[] itemPieces = input.split("\t");
                int i=0;
                String heading = itemPieces[i];
                String description = itemPieces[++i];
                String author = itemPieces[++i];
                String dateString = itemPieces[++i];

                LocalDate date = LocalDate.parse(dateString, formatter);
                NoteItem noteItem = new NoteItem(heading, description, author, date);

                noteItems.add(noteItem);
            }
        } finally {
            if(myBufferedReader != null){
                myBufferedReader.close();
            }
        }
    }

    public void storeNoteItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try{
            Iterator<NoteItem> iter = noteItems.iterator();
            while(iter.hasNext()){
                NoteItem item = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s",
                        item.getHeading(),
                        item.getDescription(),
                        item.getAuthor(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }

        } finally {
            if(bw != null){
                bw.close();
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
