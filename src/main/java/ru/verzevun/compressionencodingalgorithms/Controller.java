package ru.verzevun.compressionencodingalgorithms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ComboBox<String> inputEncoding;
    @FXML
    private ComboBox<String> outputEncoding;
    @FXML
    private TextArea inputText;
    @FXML
    private TextArea outputText;
    @FXML
    private Button conversionButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputEncoding.setItems(FXCollections.observableArrayList("ASCII", "KOI8-R", "UTF-8", "Windows-1251"));
        outputEncoding.setItems(FXCollections.observableArrayList("ASCII", "KOI8-R", "UTF-8", "Windows-1251"));
    }

    public void clickOnConversionButton() {
        byte[] bytes = inputText.getText().getBytes(Charset.forName(inputEncoding.getValue()));
        outputText.setText(new String(bytes, Charset.forName(outputEncoding.getValue())));
    }
}

