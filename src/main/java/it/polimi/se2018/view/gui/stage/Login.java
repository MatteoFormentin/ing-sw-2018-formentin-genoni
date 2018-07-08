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


/**
 * class that handle the EventPreGame to the server
 *
 * @author Luca Genoni
 */
public class Login {

    private Stage stage;
    private Scene loginScene;
    private LoginRequest packet;



    /**
     * Constructor
     *
     * @param owner of the stage for this class
     */
    public Login() {
        GridPane form = new GridPane();
        loginScene = new Scene(form, 250, 150);
        //gridPane design
        form.setAlignment(Pos.CENTER);
        form.setHgap(5);
        form.setVgap(10);


        //GridPane children
        TextField nameInput = new TextField();
        Label ip = new Label("Nickname:");
        ip.setLabelFor(nameInput);
        form.addRow(0, ip, nameInput);

        //escape
        Button connect = new Button("EventPreGame");
        Button back = new Button("Back");
        form.addRow(2, back, connect);
        //components action
        connect.setOnAction(e -> {
            //TODO creare il pachetto di login e inviarlo
            packet = new LoginRequest(nameInput.getText());
            stage.close();
        });
        back.setOnAction(e -> stage.close());
    }

    /**
     * for get the stage of the waiting
     *
     * @return the stage where the waiting is running
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Method for display the login
     *
     * @return true if login is successful, false otherwise
     */
    public LoginRequest display(Stage owner) {
        packet=null;
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);
        stage.setScene(loginScene);
        stage.showAndWait();
        return packet;
    }
}
