package it.polimi.se2018.view.gui.stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WaitGame {
    public void displayMessage(Stage stage){
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        Label wait = new Label("Aspetta gli altri giocatori");
        wait.setStyle("-fx-background-color: #888888");
        // group.getChildren().add(layoutMessage);
        Scene boxMessage =new Scene(wait,400,200,Color.BLACK);
        boxMessage.setCursor(Cursor.WAIT);
        stage.setScene(boxMessage);
        stage.show();
    }
}
