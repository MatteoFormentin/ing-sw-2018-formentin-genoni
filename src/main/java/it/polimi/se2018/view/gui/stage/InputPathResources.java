package it.polimi.se2018.view.gui.stage;

import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;
import it.polimi.se2018.view.gui.ControllerGUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InputPathResources {
    private boolean answer;
    private Stage stage;
    private Scene scene;
    private String path;

    /**
     * class for check the path of the resources for the game
     */
    public InputPathResources() {
        GridPane form = new GridPane();
        scene = new Scene(form, 250, 150);
        answer = false;
        //gridPane design
        form.setAlignment(Pos.CENTER);
        form.setHgap(5);
        form.setVgap(10);
        Text rightPath= new Text("Il percorso iniziale prima dalla cartella src/resources");
        rightPath.setTextAlignment(TextAlignment.CENTER);
        //GridPane children
        TextField nameInput = new TextField();
        Label ip = new Label("Resources Path: ");
        ip.setLabelFor(nameInput);
        form.addRow(0,ip,nameInput);
        //escape
        //escape
        Button connect = new Button("Connect");
        Button back = new Button("Back");
        form.addRow(3, back, connect);
        //components action
        connect.setOnAction(e -> {
            path=nameInput.getText();
            stage.close();
        });
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
        stage.setScene(scene);
        stage.setOnCloseRequest(null);
        stage.showAndWait();
        return path;
    }
}
