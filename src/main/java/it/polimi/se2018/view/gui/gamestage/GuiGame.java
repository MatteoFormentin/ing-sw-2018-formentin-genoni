package it.polimi.se2018.view.gui.gamestage;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.GuiReceiver;
import it.polimi.se2018.view.gui.stage.AlertMessage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GuiGame {
    private Stage stage;
    private static GuiReceiver guiReceiver;
    private BorderPane box;
    private boolean change;
    private Text confirmMessage = new Text();
    public void displayGame() {
        //thread for the update

        //stage
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.setMinWidth(250);
        stage.setMinHeight(250);
        //scene wait
        confirmMessage.setText("test");
     //   confirmMessage.setText("test");

        box = new BorderPane();
        box.setCenter(confirmMessage);
        Scene wait = new Scene(box, 400, 200, Color.BLACK);
        wait.setFill(Color.BROWN);
        stage.setScene(wait);
        stage.show();
    }

    public void game() {
        confirmMessage.setText("ciao");
        new AlertMessage().displayMessage("testo arrivato");
    }
}


    /*
    private void startUpdate(){

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
                new Runnable() {
                    int counter = 0;
                    @Override
                    public void run() {
                        counter++;
                        if (counter <= 10) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    confirmMessage.setText(
                                            "isFxApplicationThread: "
                                                    + Platform.isFxApplicationThread() + "\n"
                                                    + "Counting: "
                                                    + String.valueOf(counter));
                                }
                            });
                        } else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    counter=0;
                                    confirmMessage.setText(String.valueOf(guiReceiver.isGameStart()));
                                }
                            });
                        }

                    }
                },
                1,
                200,
                TimeUnit.MILLISECONDS);
    }
*/
