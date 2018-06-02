package it.polimi.se2018.view.prova_gui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * stage for show an important message that need a confirm from the player
 *
 * @author Luca Genoni
 */
public class ConfirmBox {
    private static boolean answer;

    public static boolean displayMessage(String message){
        Stage stageMessage = new Stage(StageStyle.UTILITY);
        stageMessage.initModality(Modality.APPLICATION_MODAL);
        stageMessage.setAlwaysOnTop(true);
        Background confirmBackground = new Background(new BackgroundFill(Color.web("#bbb"), CornerRadii.EMPTY, Insets.EMPTY));
        stageMessage.setMinWidth(250);
        stageMessage.setMinHeight(250);

        Label confirmMessage =new Label();
        confirmMessage.setText(message);
        Button yesButton =new Button("Si");
        Button noButton =new Button("No");
        yesButton.setOnAction(e->{
            answer = true;
            stageMessage.close();
        });
        noButton.setOnAction(e->{
            answer = false;
            stageMessage.close();
        });
        yesButton.setDefaultButton(false);
        noButton.setDefaultButton(false);
        VBox layoutMessage = new VBox(20);
        HBox buttonLine =new HBox(20);
        buttonLine.getChildren().addAll(yesButton,noButton);
        buttonLine.setAlignment(Pos.CENTER);
        buttonLine.backgroundProperty().setValue(confirmBackground);
        layoutMessage.getChildren().addAll(confirmMessage,buttonLine);
        layoutMessage.setAlignment(Pos.CENTER);
        layoutMessage.backgroundProperty().setValue(confirmBackground);
        // group.getChildren().add(layoutMessage);
        Scene boxMessage =new Scene(layoutMessage,400,200,Color.BLACK);
        boxMessage.setFill(Color.BROWN);
        stageMessage.setScene(boxMessage);
        stageMessage.showAndWait();


        return answer;
    }
}