package it.polimi.se2018.view.gui.stage;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.se2018.view.gui.GuiReceiver.closeProgram;

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
        stage.setOnCloseRequest(e->{
            e.consume();
            closeProgram();
        });
    }
    public void displayMessage(){
        Label wait = new Label("Ti sei aggiunto ad una partita\nAspetta che la stanza sia pronta");
        wait.setAlignment(Pos.CENTER);
        Scene scene =new Scene(wait,400,200,Color.RED);
        scene.setCursor(Cursor.WAIT);
        stage.setScene(scene);
        stage.show();
    }
    public void closeWait(){
        stage.close();
    }
}
