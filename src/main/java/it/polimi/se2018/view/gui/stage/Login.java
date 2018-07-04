package it.polimi.se2018.view.gui.stage;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.se2018.view.gui.GuiInstance.getGuiInstance;

/**
 * class that handle the Login to the server
 *
 * @author Luca Genoni
 */
public class Login {
    private boolean answer;
    private Stage stage;

    /**
     * Constructor
     *
     * @param owner of the stage for this class
     */
    public Login(Stage owner) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);
        answer = false;
    }

    /**
     * Method for display the login
     *
     * @return true if login is successful, false otherwise
     */
    public boolean display() {
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
        Label ip = new Label("Nickname:");
        ip.setLabelFor(nameInput);
        form.addRow(0, ip, nameInput);

        //escape
        Button connect = new Button("Login");
        Button back = new Button("Back");
        form.addRow(2, back, connect);
        //components action
        connect.setOnAction(e -> {
            if (getGuiInstance().getFactoryInstance() == null) {
                try {
                    if (getGuiInstance().getClient().login(nameInput.getText())) {
                        answer = true;
                        stage.close();
                    } else new AlertMessage(stage).displayMessage("Non puoi eseguire il login");

                } catch (NullPointerException ex) {
                    new AlertMessage(stage).displayMessage("Non sei collegato al server. \n" +
                            "Imposta prima il server a cui ti vuoi collegare");
                }
            } else {
                try {
                    getGuiInstance().getClient2().login2(nameInput.getText());
                    answer = true;
                    stage.close();
                } catch (ConnectionProblemException | PlayerAlreadyLoggedException | RoomIsFullException ex) {
                    new AlertMessage(stage).displayMessage(ex.getMessage());
                }
            }
        });
        back.setOnAction(e -> stage.close());
        stage.showAndWait();
        return answer;
    }
}
