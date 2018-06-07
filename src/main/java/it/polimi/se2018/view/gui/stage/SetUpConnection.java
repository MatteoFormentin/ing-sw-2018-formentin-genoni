package it.polimi.se2018.view.gui.stage;


import it.polimi.se2018.network.client.ClientController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.InputMismatchException;

public class SetUpConnection {
    private boolean connected = false;

    public boolean display(ClientController client){
        /*StageStyle
                    UTILITY only _, x
                    TRANSPARENT NOTHING and no _, o, x
                    UNIFIED boh
                    UNDECORATED simile a TRANSPARENT ma con sfondo
          modality
                    APPLICATION_MODAL can't turn to previus stage and can't move
                    WINDOW_MODAL
        stage stageMessage = new stage(StageStyle.UTILITY);
        stageMessage.initModality(Modality.APPLICATION_MODAL);
        stageMessage.setAlwaysOnTop(true);
         */
        //static design
        Stage stage = new Stage(StageStyle.UNDECORATED);
        GridPane form =new GridPane();
        Scene scene =new Scene(form,250,150);
        stage.setScene(scene);

        //stage design
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);

        //gridPane design
        form.setAlignment(Pos.CENTER);
        form.setHgap(5);
        form.setVgap(10);


        //GridPane children
        TextField ipInput = new TextField();
        Label ip =new Label("Server IP :");
        ip.setLabelFor(ipInput);
        form.addRow(0,ip,ipInput);
        //port
        TextField portInput = new TextField();
        Label port =new Label("Server Port :");
        ip.setLabelFor(portInput);
        form.addRow(1,port,portInput);
        //escape
        Button connect = new Button("Connect");
        Button back = new Button ("Back");
        form.addRow(2,back,connect);
        //components action
        connect.setOnAction(e-> {
            try {
                if(isInt(portInput)){
                    connected = client.startRMIClient(ipInput.getText(),Integer.parseInt(portInput.getText()));
                    if (connected) {
                        new AlertMessage(stage).displayMessage("Dati del server corretti");
                        stage.close();
                    }
                    else new AlertMessage(stage).displayMessage("Non è stato trovato il server");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                new AlertMessage(stage).displayMessage("Errore inaspettato durante la connessione");
            }
        });
        back.setOnAction(e->{
            connected = false;
            stage.close();
        } );

        stage.showAndWait();
        return connected;
    }

    private boolean isInt(TextField input){
        try{
            int port = Integer.parseInt(input.getText());
            return true;
        }catch(NumberFormatException e){
            System.out.println("errore, c'è un testo");
            input.setStyle("-fx-text-inner-color: red;");
            return false;
        }
    }
}
