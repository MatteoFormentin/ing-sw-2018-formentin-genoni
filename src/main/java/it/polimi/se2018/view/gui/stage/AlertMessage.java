package it.polimi.se2018.view.gui.stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * stage for show an important message
 *
 * @author Luca Genoni
 */
public class AlertMessage {
    private Stage stageMessage;

    /**
     * Constructor
     *
     * @param owner of the stage for this class
     */
    public AlertMessage(Stage owner) {
        stageMessage = new Stage(StageStyle.UTILITY);
        stageMessage.initModality(Modality.APPLICATION_MODAL);
        stageMessage.initOwner(owner);
    }

    /**
     * Method for display the alert Box
     *
     * @param message the message to show
     */
    public void displayMessage(String message) {
        /*StageStyle
                    UTILITY only _ x
                    TRANSPARENT NOTHING and no _ o x
                    UNIFIED boh
                    UNDECORATED simile a TRANSPARENT ma con sfondo
          modality
                    APPLICATION_MODAL can't turn to previus stage and can't move
                    WINDOW_MODAL
         */
        Background focusBackground = new Background(new BackgroundFill(Color.web("#bbb"), CornerRadii.EMPTY, Insets.EMPTY));
        Label errorMessage = new Label();
        errorMessage.setText(message);
        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> stageMessage.close());
        VBox layoutMessage = new VBox(20);
        layoutMessage.getChildren().addAll(errorMessage, closeButton);
        layoutMessage.setAlignment(Pos.CENTER);
        layoutMessage.setBackground(focusBackground);
        Scene boxMessage = new Scene(layoutMessage, 400, 200, Color.BLACK);
        boxMessage.setFill(Color.BROWN);
        stageMessage.setScene(boxMessage);
        stageMessage.showAndWait();
    }
}