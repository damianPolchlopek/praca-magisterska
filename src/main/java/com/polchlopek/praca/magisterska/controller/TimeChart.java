package com.polchlopek.praca.magisterska.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class TimeChart {

    @FXML
    private LineChart<Number, Number> lineChartTime;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    public void initialize() {
        xAxis.setLabel("Time [s]");
        yAxis.setLabel("Temperature [C]");

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>("Measurement 1",
                FXCollections.observableArrayList(
                        plot(23, 14, 15, 24, 34, 36, 22, 45, 43, 17, 29, 25)
                )
        );

        lineChartTime.getData().addAll(series);

    }

    public ObservableList<XYChart.Data<Number, Number>> plot(int... y) {
        ObservableList<XYChart.Data<Number, Number>> dataset = FXCollections.observableArrayList();

        for(int i=0; i<y.length; ++i){
            final XYChart.Data<Number, Number> data = new XYChart.Data<>(i + 1, y[i]);
            data.setNode(
                    new HoveredThresholdNode(y[i])
            );
            dataset.add(data);
        }

        return dataset;
    }


    /**
     * a node which displays a value on hover, but is otherwise empty
     */

    class HoveredThresholdNode extends StackPane {
        HoveredThresholdNode(int value) {
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

        private Label createDataThresholdLabel(int value) {
            final Label label = new Label(value + "");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }

    }
}
