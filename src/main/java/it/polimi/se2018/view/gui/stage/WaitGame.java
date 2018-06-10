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

/**
 * Class for handle the waiting
 *
 * @author Luca Genoni
 */
public class WaitGame {
    private Stage stage;

    /**
     * for get the stage of the waiting
     *
     * @return the stage where the waiting is running
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Constructor
     *
     * @param owner the owner of the waiting stage
     */
    public WaitGame(Stage owner) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    /**
     * show the stage for wait something
     *
     * @param messageWait the message to show while the waiting is shown
     */
    public void displayMessage(String messageWait) {
        Label wait = new Label(messageWait);
        wait.setAlignment(Pos.CENTER);
        Scene scene = new Scene(wait, 400, 200, Color.RED);
        scene.setCursor(Cursor.WAIT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * close the wait
     */
    public void closeWait() {
        stage.close();
    }
}
