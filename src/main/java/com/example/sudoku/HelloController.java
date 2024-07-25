package com.example.sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private GridPane matrix;

    @FXML
    private Text attemptsRemaining;

    private Button selectedNumber;

    private final int[][] BACKUP_MATRIX = {
            { 4,3,1,6,7,9,5,2,8 },
            { 9,6,7,2,5,8,3,4,1 },
            { 5,8,2,1,4,3,9,6,7 },
            { 6,5,9,8,1,7,2,3,4 },
            { 3,2,8,5,6,4,1,7,9 },
            { 7,1,4,9,3,2,8,5,6 },
            { 8,7,3,4,2,1,6,9,5 },
            { 1,4,5,3,9,6,7,8,2 },
            { 2,9,6,7,8,5,4,1,3 }
    };
    private final boolean[][] HIDE = {
            { true,false,false,false,true,true,false,true,false },
            { false,false,false,false,false,false,false,false,false },
            { true,true,true,false,true,false,false,false,false },
            { false,false,false,false,false,false,false,false,true },
            { true,false,false,false,true,true,false,true,false },
            { false,false,false,false,false,false,false,false,false },
            { true,true,true,false,true,false,false,false,false },
            { false,false,false,false,false,false,false,false,true },
            { true,false,false,false,true,true,false,true,false }
    };

    private final int[][] MATRIX = BACKUP_MATRIX;
    private int[][] currentMatrix;

    private final int MAX_ATTEMPTS = 3;
    private int attempt = MAX_ATTEMPTS;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int row = 0; row < MATRIX.length; row++) {
            for (int col = 0; col < MATRIX[row].length; col++) {
                Text number = new Text(!HIDE[row][col] ? String.valueOf(MATRIX[row][col]) : " ");
                number.setFont(new Font("Fira Code Regular", 18));
                number.setTranslateX(col < 3 ? 20 : 23);
                matrix.add(number, col, row);
            }
        }
        currentMatrix = new int[MATRIX.length][MATRIX.length];
    }

    @FXML
    protected void onClick(MouseEvent event)
    {
        if (attempt == 0)
            return;

        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != matrix) {
            Integer col = GridPane.getColumnIndex(clickedNode);
            Integer row = GridPane.getRowIndex(clickedNode);
            if (clickedNode instanceof Text) {
                if (currentMatrix[row][col] == 0 || currentMatrix[row][col] == -1) {
                    currentMatrix[row][col] = selectedNumber != null ? Integer.parseInt(selectedNumber.getText()) : 1;

                    boolean mistake = currentMatrix[row][col] != MATRIX[row][col];
                    Text replace = (Text) clickedNode;
                    replace.setText(String.valueOf(currentMatrix[row][col]));
                    if (mistake) {
                        --attempt;
                        attemptsRemaining.setText(attempt + "/" + MAX_ATTEMPTS);
                        currentMatrix[row][col] = -1;
                    }
                }
                highlight();
            }
        }
    }

    @FXML
    protected void onSelectNumberClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        selectedNumber = source;
        highlight();
    }

    private void highlight() {
        matrix.getChildren().forEach(cell -> {
            if (cell instanceof Text) {
                Text display = (Text) cell;
                if (currentMatrix[GridPane.getRowIndex(cell)][GridPane.getColumnIndex(cell)] == -1)
                    display.setFill(Color.RED);
                else if (selectedNumber.getText().equals(display.getText()))
                    display.setFill(Color.BLUE);
                else
                    display.setFill(Color.BLACK);
            }
        });
    }
}