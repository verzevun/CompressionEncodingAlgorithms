module ru.verzevun.compressionencodingalgorithms {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.albfernandez.juniversalchardet;

    opens ru.verzevun.compressionencodingalgorithms to javafx.fxml;
    exports ru.verzevun.compressionencodingalgorithms;
}