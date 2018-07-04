package it.polimi.se2018.view.gui.stage;


import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.exception.network_exception.NoPortRightException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

import static it.polimi.se2018.view.gui.GuiInstance.getGuiInstance;


/**
 * Class for handle the setup of the connection
 *
 * @author Luca Genoni
 */
public class SetUpConnection {
    private Stage stage;

    /**
     * Constructor
     *
     * @param owner of the stage for this class
     */
    public SetUpConnection(Stage owner) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);
    }

    /**
     * Method for display the stage
     */
    public void display() {
        GridPane form = new GridPane();
        Scene scene = new Scene(form, 250, 150);
        stage.setScene(scene);

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
            try {
                if (getGuiInstance().getFactoryInstance() == null) getGuiInstance().getClient().startClient(ipInput.getText(), i);
                else {
                    AbstractClient2 client = getGuiInstance().getFactoryInstance().createClient(getGuiInstance(), ipInput.getText(), Integer.parseInt(portInput2.getText()), i);
                    client.connectToServer2();
                    getGuiInstance().setClient2(client);
                }
                stage.close();
            } catch (IOException ex) {
                new AlertMessage(stage).displayMessage(ex.getMessage());
            } catch (NoPortRightException ex) {
                new AlertMessage(stage).displayMessage(ex.getMessage());
            }  catch (Exception ex) {
                new AlertMessage(stage).displayMessage(ex.getMessage());
            }
        });
        back.setOnAction(e -> stage.close());
        stage.showAndWait();
    }

    private boolean isInt(TextField input) {
        try {
            Integer.parseInt(input.getText());
            input.setStyle("-fx-text-inner-color: black;");
            return true;
        } catch (NumberFormatException e) {
            input.setStyle("-fx-text-inner-color: red;");
            return false;
        }
    }
}
