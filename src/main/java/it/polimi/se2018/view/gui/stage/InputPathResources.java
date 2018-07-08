package it.polimi.se2018.view.gui.stage;

import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InputPathResources {
    private boolean answer;
    private Stage stage;
    private String path;

    /**
     * class for check the path of the resources for the game
     */
    public InputPathResources() {
        GridPane form = new GridPane();
        Scene scene = new Scene(form, 250, 150);
        stage.setScene(scene);
        answer = false;
        //gridPane design
        form.setAlignment(Pos.CENTER);
        form.setHgap(5);
        form.setVgap(10);


        //GridPane children
        TextField nameInput = new TextField();
        Label ip = new Label("Resources Path: ");
        ip.setLabelFor(nameInput);
        form.addRow(0, ip, nameInput);

        //escape

        //components action
    }

    /**
     * class for show the input of the path
     * @param owner stage owner of this stage
     */
    public String display(Stage owner) {
        stage = new Stage(StageStyle.UNIFIED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);
        stage.showAndWait();
        return path;
    }
}
