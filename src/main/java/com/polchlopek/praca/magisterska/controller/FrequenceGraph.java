package com.polchlopek.praca.magisterska.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class FrequenceGraph {

    @FXML
    private BarChart<String, Number> barChartFrequence;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    public void initialize(){


        xAxis.setLabel("Frequnce [Hz]");
        yAxis.setLabel("Magnitude [-]");

        barChartFrequence.setCategoryGap(130);

        //defining a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Plot 1");

        //populating the series with data
        series.getData().add(new XYChart.Data<>("1", 23));
        series.getData().add(new XYChart.Data<>("2", 18));
        series.getData().add(new XYChart.Data<>("3", 10));
        series.getData().add(new XYChart.Data<>("4", 30));


        barChartFrequence.getData().addAll(series);

    }

}
