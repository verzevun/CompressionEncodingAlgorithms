package ru.verzevun.compressionencodingalgorithms;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ComboBox<String> inputEncoding;
    @FXML
    private ComboBox<String> outputEncoding;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea outputTextArea;
    private byte[] messageInByte;
    private final HashMap<Integer, Character> Win1251 = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputEncoding.setItems(FXCollections.observableArrayList("ASCII", "KOI8-R", "UTF-8", "Windows-1251"));
        outputEncoding.setItems(FXCollections.observableArrayList("ASCII", "KOI8-R", "UTF-8", "Windows-1251"));
        for (int i = 0; i < 256; i++) {
            byte[] a = {(byte) i};
            String str = new String(a, getEncoding("Windows-1251"));
            Win1251.put(i, str.charAt(0));
        }
    }

    public Charset getEncoding(String selectEncoding) {
        return Charset.forName(selectEncoding);
    }

    public void clickOnFileSelectButton() throws IOException {
        FileChooser fileChooser = new FileChooser();
        encodingAutoDetection(fileChooser.showOpenDialog(Application.getStage()));
        inputTextArea.setText(new String(messageInByte, getEncoding(inputEncoding.getValue())));
    }

    public void Win1251ToASCII() {
        char error = '�';
        StringBuilder convertMessage = new StringBuilder();
        for (byte b : messageInByte) {
            if (b >= 0) convertMessage.append(Win1251.get((int) b));
            else convertMessage.append(error);
        }
        outputTextArea.setText(String.valueOf(convertMessage));
    }

    public void ASCIIToWin1251() {
        outputTextArea.setText(new String(messageInByte, getEncoding(inputEncoding.getValue())));
    }

    public void clickOnConversionButton() {
        if (inputEncoding.getValue().equals("Windows-1251") && outputEncoding.getValue().equals("ASCII")) {
            Win1251ToASCII();
        } else if ((inputEncoding.getValue().equals("ASCII") && outputEncoding.getValue().equals("Windows-1251"))) {
            ASCIIToWin1251();
        } else {
            outputTextArea.setText(new String(inputTextArea.getText().getBytes(getEncoding(inputEncoding.getValue())), getEncoding(outputEncoding.getValue())));
        }
    }

    public void encodingAutoDetection(File file) throws IOException {
        switch (UniversalDetector.detectCharset(file)) {
            case "US-ASCII" -> inputEncoding.setValue("ASCII");
            case "KOI8-R" -> inputEncoding.setValue("KOI8-R");
            case "WINDOWS-1251" -> inputEncoding.setValue("Windows-1251");
            case "UTF-8" -> inputEncoding.setValue("UTF-8");
        }
        messageInByte = Files.readAllBytes(file.toPath());
    }
}
