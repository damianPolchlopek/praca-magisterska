package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.config.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jssc.SerialPort.MASK_RXCHAR;

public class STMCommunication {

    @FXML
    private Label labelValue;

    @FXML
    private ComboBox comboBoxPorts;

    @FXML
    private ToggleButton diode;

    @FXML
    private LineChart<Number, Number> lineChartTime;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    // Radio buttony odzpowiedzialne za wybor algorytmu
    @FXML
    private RadioButton FFT;

    @FXML
    private RadioButton FFT_Hamming;

    @FXML
    private RadioButton None;

    // zmienne odpowedzialne za liczbe probek
    @FXML
    private TextField amountProbes;

    @FXML
    private Label amountProbesError;



    private ObservableList<String> portList;
    private SerialPort stmPort = null;
    final int NUM_OF_POINT = 50;
    XYChart.Series series;
    boolean sendingResult = false;


    // USTAWIANIE PARAMETROW ALGORYTMOW

    @FXML
    public void setTypeOfAlgorithm(){

        try {
            if(stmPort != null){
                if(FFT.isSelected()) {
                    stmPort.writeString("FFT");
                }

                if(FFT_Hamming.isSelected()) {
                    stmPort.writeString("FFT_Hamming");
                }

                if(None.isSelected()) {
                    stmPort.writeString("None");
                }

            }
        }catch (SerialPortException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    private static boolean isPowerOfTwo(int number) {

        if (number % 2 != 0) {
            return false;
        } else {
            for (int i = 0; i <= number; i++) {
                if (Math.pow(2, i) == number) return true;
            }
        }
        return false;
    }


    @FXML
    public void setAmountProbes(){

        int amountProbesToSend = Integer.parseInt(amountProbes.getText().trim());

        if(isPowerOfTwo(amountProbesToSend)){
            System.out.println("Lb probek: " + amountProbesToSend);

            amountProbesError.setText("");
        }
        else {
            amountProbesError.setText("Liczba musi byc potega 2 !!!");
        }


    }




    // METODY ODPOWIEDZIALNE ZA KOMMUNIKACJE

    @FXML
    public void disconnect(){
        disconnectSTM();
    }

    @FXML
    public void searchPort(){

        detectPort();
        comboBoxPorts.setItems(portList);

        comboBoxPorts.valueProperty()
                .addListener(new ChangeListener<String>() {

                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {

                        System.out.println(newValue);
                        disconnectSTM();
                        connectSTM(newValue);
                    }

                });
    }

    @FXML
    private void toggleDiode(){
        try {
            if(diode.isSelected()){
                if(stmPort != null){
                    stmPort.writeString("1");
                    System.out.println("LED 13 ON");
                }else{
                    System.out.println("stm not connected!");
                }
            }else {
                if(stmPort != null){
                    stmPort.writeString("2");
                    System.out.println("LED 13 OFF");
                }else{
                    System.out.println("stm not connected!");
                }
            }
        }catch (SerialPortException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private boolean connectSTM(String port){

        boolean success = false;
        SerialPort serialPort = new SerialPort(port);
        AtomicBoolean sendingResult = new AtomicBoolean(false);

        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);

            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
                if(serialPortEvent.isRXCHAR()){
                    try {
                        byte[] b = serialPort.readBytes();
                        String message = "";

                        if (b.length != 0){
                            for (byte c: b){
                                message += (char)c;
                            }

                            if (message.contains("wysylanieWyniku:START")){
                                sendingResult.set(true);
                            }

                            if (message.contains("wysylanieWyniku:STOP")){
                                sendingResult.set(false);
                            }

                            System.out.println("*********************************************");
                            System.out.println("Wiadomosc przychodzaca: " + message);
                            System.out.println("*********************************************");

                            String st = String.valueOf(message);
                            try {
                                float sendedValue = Float.parseFloat(message);

                                //Update label in ui thread
                                Platform.runLater(() -> {
                                    labelValue.setText(st);

                                    if(!sendingResult.get()){
                                        shiftSeriesData(sendedValue);
                                    }

                                });
                            }
                            catch (NumberFormatException e){

                            }
                        }


                    } catch (SerialPortException ex) {
                        Logger.getLogger(Main.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                }
            });

            stmPort = serialPort;
            success = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("SerialPortException: " + ex.toString());
        }

        return success;
    }

    private void disconnectSTM(){

        if(stmPort != null){
            try {
                stmPort.removeEventListener();

                if(stmPort.isOpened()){
                    stmPort.closePort();
                }

            } catch (SerialPortException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    private void detectPort(){

        portList = FXCollections.observableArrayList();
        String[] serialPortNames = SerialPortList.getPortNames();
        for(String name: serialPortNames){
            portList.add(name);
        }

    }

    public void shiftSeriesData(float newValue)
    {
        for(int i=0; i<NUM_OF_POINT-1; i++){
            XYChart.Data<String, Number> ShiftDataUp =
                    (XYChart.Data<String, Number>)series.getData().get(i+1);
            Number shiftValue = ShiftDataUp.getYValue();
            XYChart.Data<String, Number> ShiftDataDn =
                    (XYChart.Data<String, Number>)series.getData().get(i);
            ShiftDataDn.setYValue(shiftValue);
        }
        XYChart.Data<String, Number> lastData =
                (XYChart.Data<String, Number>)series.getData().get(NUM_OF_POINT-1);
        lastData.setYValue(newValue);
    }

    public void drawChart(){
        series = new XYChart.Series();
        series.setName("A0 analog input");
        lineChartTime.getData().add(series);
        lineChartTime.setAnimated(false);
        xAxis.setLabel("Przyspieszenie");
        yAxis.setLabel("Temperature [C]");

        //pre-load with dummy data
        for(int i=0; i<NUM_OF_POINT; i++){
            series.getData().add(new XYChart.Data(i, 0));
        }

    }

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
