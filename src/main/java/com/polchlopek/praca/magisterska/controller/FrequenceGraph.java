package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DTO.ReceivedDataFromSTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class FrequenceGraph {

    @FXML
    private BarChart<Number, Number> barChartFrequence;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

    public void initialize(){


        xAxis.setLabel("Frequnce [Hz]");
        yAxis.setLabel("Magnitude [-]");

        //barChartFrequence.setCategoryGap(130);

        //populating the series with data
        //defining a series
        XYChart.Series series = new XYChart.Series("Measurement 1",
                FXCollections.observableArrayList(
                        plot(ReceivedDataFromSTM.getInstance().getList())
                )
        );


        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
//        series1.getData().add(new XYChart.Data(Integer.toString(1), 25601.34));
//        series1.getData().add(new XYChart.Data(Integer.toString(2), 20148.82));
//        series1.getData().add(new XYChart.Data(france, 10000));
//        series1.getData().add(new XYChart.Data(italy, 35407.15));
//        series1.getData().add(new XYChart.Data(usa, 12000));

//        for (int i = 1; i <= ReceivedDataFromSTM.getInstance().getList().size(); ++i) {
//            series1.getData().add(new XYChart.Data(Integer.toString(i),
//                                                    ReceivedDataFromSTM.getInstance().getList().get(i-1)));
//        }


        barChartFrequence.getData().addAll(series);

    }

    public ObservableList<XYChart.Data<String, Number>> plot(ArrayList<Float> y) {
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


    /**
     * a node which displays a value on hover, but is otherwise empty
     */

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
    }

        private Label createDataThresholdLabel(float value) {
            final Label label = new Label(value + "");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }

}
