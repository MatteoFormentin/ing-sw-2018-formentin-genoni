package it.polimi.se2018.view.prova_gui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * stage for show an important message
 *
 * @author Luca Genoni
 */
public class AlertMessage {

    public static void displayMessage(String message){
        /*StageStyle
                    UTILITY only _ x
                    TRANSPARENT NOTHING and no _ o x
                    UNIFIED boh
                    UNDECORATED simile a TRANSPARENT ma con sfondo
          modality
                    APPLICATION_MODAL can't turn to previus stage and can't move
                    WINDOW_MODAL
         */
        Stage stageMessage = new Stage(StageStyle.UNDECORATED);
        stageMessage.initModality(Modality.APPLICATION_MODAL);
        Background focusBackground = new Background(new BackgroundFill(Color.web("#bbb"), CornerRadii.EMPTY, Insets.EMPTY));


        Label errorMessage =new Label();
        errorMessage.setText(message);

        Button closeButton =new Button("Ok");
        closeButton.setOnAction(e->stageMessage.close());


        VBox layoutMessage = new VBox(20);
        layoutMessage.getChildren().addAll(errorMessage,closeButton);
        layoutMessage.setAlignment(Pos.CENTER);
        layoutMessage.setBackground(focusBackground);
       // group.getChildren().add(layoutMessage);
        Scene boxMessage =new Scene(layoutMessage,400,200,Color.BLACK);
        boxMessage.setFill(Color.BROWN);
        stageMessage.setScene(boxMessage);
        stageMessage.showAndWait();
    }
}
/*/*StageStyle
                    UTILITY only _ x
                    TRANSPARENT NOTHING and no _ o x
                    UNIFIED boh
                    UNDECORATED simile a TRANSPARENT ma con sfondo
          modality
                    APPLICATION_MODAL can't turn to previus stage and can't move
                    WINDOW_MODAL

final Stage stageMessage = new Stage(StageStyle.UTILITY);
        stageMessage.initModality(Modality.APPLICATION_MODAL);


                Label errorMessage =new Label();
                errorMessage.setText(message);
                Button closeButton =new Button("Ok");
                closeButton.setOnAction(e->stageMessage.close());


                VBox layoutMessage = new VBox(20);
                layoutMessage.getChildren().addAll(errorMessage,closeButton);
                layoutMessage.setAlignment(Pos.CENTER);

                Scene boxMessage =new Scene(layoutMessage,200,200,Color.DARKGRAY);
                boxMessage.setFill(Color.BROWN);
                stageMessage.setScene(boxMessage);
                stageMessage.showAndWait();*/