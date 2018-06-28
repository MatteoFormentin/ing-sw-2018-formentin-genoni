package it.polimi.se2018.view.gui.stage;


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
        GridPane form2 = new GridPane();
        Scene scene = new Scene(form, 250, 150);
        Scene scene2 = new Scene(form2, 250, 150);
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
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("RMI");
        rb1.setUserData(0);
        rb1.setToggleGroup(group);
        rb1.setSelected(true);


        RadioButton rb2 = new RadioButton("SOCKET");
        rb2.setUserData(1);
        rb2.setToggleGroup(group);

        form.addRow(1, rb1, rb2);
        //escape
        Button connect = new Button("Connect");
        Button back = new Button("Back");
        form.addRow(2, back, connect);

        //gridPane2 design
        form2.setAlignment(Pos.CENTER);
        form2.setHgap(5);
        form2.setVgap(10);
        //GridPane children
        TextField ipInput2 = new TextField();
        Label ip2 = new Label("Server IP :");
        ip2.setLabelFor(ipInput);
        form2.addRow(0, ip2, ipInput2);
        //port
        TextField portInput2 = new TextField();
        Label port2 = new Label("Server Port :");
        ip2.setLabelFor(portInput2);
        form2.addRow(1, port2, portInput2);
        //escape
        Button connect2 = new Button("Connect");
        Button back2 = new Button("Back");
        form2.addRow(2, back2, connect2);


        //components action
        connect.setOnAction(e -> {
            int i = Integer.parseInt(group.getSelectedToggle().getUserData().toString());
            try {
                getGuiInstance().getClient().startClient(ipInput.getText(), i);
                stage.close();
            } catch (IOException ex) {
                stage.setScene(scene2);
                new AlertMessage(stage).displayMessage(ex.getMessage());
            } catch (NoPortRightException ex) {
                stage.setScene(scene2);
                new AlertMessage(stage).displayMessage(ex.getMessage());
            }  catch (Exception ex) {
                new AlertMessage(stage).displayMessage(ex.getMessage());
            }
        });
        connect2.setOnAction(e -> {
                if (isInt(portInput2)) {
                    try{
                        getGuiInstance().getClient().startClient(ipInput.getText(), Integer.parseInt(portInput2.getText()));
                        stage.close();
                    }catch(Exception ex){
                        new AlertMessage(stage).displayMessage(ex.getMessage());
                    }
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
