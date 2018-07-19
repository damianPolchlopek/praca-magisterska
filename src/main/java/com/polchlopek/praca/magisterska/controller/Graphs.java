package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DTO.ReceivedDataFromSTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Graphs {


    // TIME GRAPH
    @FXML
    private LineChart<Number, Number> lineChartTime;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    // FREQUENCE GRAPH
    @FXML
    private BarChart<Number, Number> barChartFrequence;

    @FXML
    private CategoryAxis xAxisBarGraph;

    @FXML
    private NumberAxis yAxisBarGraph;


    public void initialize() {

        //////////////////////////////////////////////////////////////////////////////////////////
        // TIME GRAPH

        xAxis.setLabel("Time [s]");
        yAxis.setLabel("Temperature [C]");

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>("Measurement 1",
                FXCollections.observableArrayList(
                        plot(ReceivedDataFromSTM.getInstance().getList())
                )
        );

        lineChartTime.getData().addAll(series);


        //////////////////////////////////////////////////////////////////////////////////////////
        // FREQUENCE GRAPH

        xAxisBarGraph.setLabel("Frequnce [Hz]");
        yAxisBarGraph.setLabel("Magnitude [-]");

        XYChart.Series seriesFreq = new XYChart.Series("Measurement 1",
                FXCollections.observableArrayList(
                        plotFreq(ReceivedDataFromSTM.getInstance().getList())
                )
        );

        barChartFrequence.getData().addAll(seriesFreq);

        //////////////////////////////////////////////////////////////////////////////////////////


    }

    public ObservableList<XYChart.Data<Number, Number>> plot(ArrayList<Float> y) {
        ObservableList<XYChart.Data<Number, Number>> dataset = FXCollections.observableArrayList();

        for(int i=0; i<y.size(); ++i){
            final XYChart.Data<Number, Number> data = new XYChart.Data<>(i + 1, y.get(i));
            data.setNode(
                    new HoveredThresholdNode(y.get(i))
            );
            dataset.add(data);
        }

        return dataset;
    }


    public ObservableList<XYChart.Data<String, Number>> plotFreq(ArrayList<Float> y) {
        ObservableList<XYChart.Data<String, Number>> dataset = FXCollections.observableArrayList();

        for(int i=0; i<y.size(); ++i){
            final XYChart.Data<String, Number> data = new XYChart.Data<>(Integer.toString(i + 1), y.get(i));
            data.setNode(
                    new HoveredThresholdNode(y.get(i))
            );
            dataset.add(data);
        }

        return dataset;
    }


    class HoveredThresholdNode extends StackPane {
        HoveredThresholdNode(float value) {
            setPrefSize(15, 15);

            final Label label = createDataThresholdLabel(value);

            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                    setCursor(Cursor.CROSSHAIR);
                }
            });
        }

        private Label createDataThresholdLabel(float value) {
            final Label label = new Label(value + "");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }

    }
}
