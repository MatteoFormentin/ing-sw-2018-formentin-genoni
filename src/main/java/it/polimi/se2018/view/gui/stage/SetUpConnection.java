package it.polimi.se2018.view.gui.stage;


import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.alternative_network.client.ClientFactory;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.ControllerGUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Class for handle the setup of the connection
 *
 * @author Luca Genoni
 */
public class SetUpConnection {

    private Stage stage;
    private Scene setUp;
    private AbstractClient2 client2;
    private AlertMessage wrongInput;

    /**
     * build the Scene of the connection SetUp
     * @param factory
     * @param controllerGUI
     */
    public SetUpConnection(ClientFactory factory) {

        GridPane form = new GridPane();
        setUp = new Scene(form, 250, 150);
        //gridPane design
        form.setAlignment(Pos.CENTER);
        form.setHgap(5);
        form.setVgap(10);

        //GridPane children
        TextField ipInput = new TextField();
        Label ip = new Label("Server IP :");
        ip.setLabelFor(ipInput);
        form.addRow(0, ip, ipInput);
        //port
        TextField portInput2 = new TextField();
        Label port2 = new Label("Server Port :");
        port2.setLabelFor(portInput2);
        form.addRow(1, port2, portInput2);
        //rmi o socket
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("RMI");
        rb1.setUserData(0);
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("SOCKET");
        rb2.setUserData(1);
        rb2.setToggleGroup(group);

        form.addRow(2, rb1, rb2);
        //escape
        Button connect = new Button("Connect");
        Button back = new Button("Back");
        form.addRow(3, back, connect);
        //components action
        connect.setOnAction(e -> {
            int i = Integer.parseInt(group.getSelectedToggle().getUserData().toString());
            int port = isIntOrNull(portInput2);
            if (port != -1) {
                client2 = factory.createClient(ControllerGUI.getInstanceControllerGUI(), ipInput.getText(), port, i);
                client2.connectToServer2();
            } else {
                wrongInput.displayMessage("Non è stato inserito un valore ammissibile per la porta");
            }
        });
    }

    /**
     * method for ask and get the client for the connection
     *
     * @param owner the stage owner of this reconnection
     * @return the client setup
     */
    public AbstractClient2 display(Stage owner) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);
        stage.setScene(setUp);
        wrongInput = new AlertMessage(stage);
        stage.showAndWait();
        return client2;
    }

    /**
     * method that check the integrity constraints of the port
     *
     * @param input Text field to check
     * @return the integer insert, 0 if port is null,-1 if the port is unavailable
     */
    private int isIntOrNull(TextField input) {
        try {
            int i = Integer.parseInt(input.getText());
            if (i < 65535 && i>=0) return i;
            else return -1;
        } catch (NumberFormatException e) {
            if (input.getText().equals("")) return 0;
        }
        return -1;
    }
}
