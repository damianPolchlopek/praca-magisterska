package com.polchlopek.praca.magisterska.DTO;

import java.util.ArrayList;

public class ResultFromStm {

    private ArrayList<Float> myList = new ArrayList<>();


    private static ResultFromStm ourInstance = new ResultFromStm();

    public static ResultFromStm getInstance() {
        return ourInstance;
    }

    private ResultFromStm() {
    }


    public void addData(Float data){
        myList.add(data);
    }

}
