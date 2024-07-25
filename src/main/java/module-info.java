module com.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.sudoku to javafx.fxml;
    exports com.example.sudoku;
}