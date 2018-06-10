package it.polimi.se2018.view.gui.stage;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
        TextField portInput = new TextField();
        Label port = new Label("Server Port :");
        ip.setLabelFor(portInput);
        form.addRow(1, port, portInput);
        //escape
        Button connect = new Button("Connect");
        Button back = new Button("Back");
        form.addRow(2, back, connect);
        //components action
        connect.setOnAction(e -> {
            try {
                if (isInt(portInput)) {
                    if (getGuiInstance().getClient().startRMIClient(ipInput.getText(), Integer.parseInt(portInput.getText()))) {
                        new AlertMessage(stage).displayMessage("Dati del server corretti");
                        stage.close();
                    } else new AlertMessage(stage).displayMessage("Non è stato trovato il server.");
                } else {
                    new AlertMessage(stage).displayMessage("Per favore, inserisci un numero per la porta...");
                }
            } catch (Exception ex) {
                new AlertMessage(stage).displayMessage("Errore inaspettato durante il setup della connessione\n" +
                        "Oppure sono state finalmente implementate le eccezioni per il setup");
            }
        });
        back.setOnAction(e -> stage.close());
        stage.showAndWait();
    }

    private boolean isInt(TextField input) {
        try {
            Integer.parseInt(input.getText());
            return true;
        } catch (NumberFormatException e) {
            input.setStyle("-fx-text-inner-color: red;");
            return false;
        }
    }
}
