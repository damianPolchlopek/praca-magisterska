package com.polchlopek.praca.magisterska.DTO;

import java.util.ArrayList;
import java.util.List;

public class ReceivedDataFromSTM {
    private static ReceivedDataFromSTM ourInstance = new ReceivedDataFromSTM();

    public static ReceivedDataFromSTM getInstance() {
        return ourInstance;
    }


    private ArrayList<Float> probesToDraw;

    ReceivedDataFromSTM() {
        probesToDraw = new ArrayList<>();
    }

    public void addData(Float data){
        probesToDraw.add(data);
    }

    public ArrayList<Float> getList(){
        return probesToDraw;
    }

    public void clearArray(){
        probesToDraw.clear();
    }


}
