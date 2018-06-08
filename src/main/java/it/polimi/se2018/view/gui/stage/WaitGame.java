package it.polimi.se2018.view.gui.stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WaitGame {
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public WaitGame(Stage owner) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);
        stage.setOnCloseRequest(e->e.consume());
    }
    public void displayMessage(){
        Label wait = new Label("Aspetta gli altri giocatori");
        wait.setAlignment(Pos.CENTER);
        //wait.setStyle("-fx-background-color: #888888");
        // group.getChildren().add(layoutMessage);
        Scene scene =new Scene(wait,400,200,Color.RED);
        scene.setCursor(Cursor.WAIT);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void closeWait(){
        stage.close();
    }
}
