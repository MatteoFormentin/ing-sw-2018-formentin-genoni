package it.polimi.se2018.view.prova_gui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * stage for select the window.
 *
 * @author Luca Genoni
 */
public class WindowSelection {
    public static void display(){
        Stage stageWindowChoice =new Stage();

        stageWindowChoice.initModality(Modality.APPLICATION_MODAL);
        stageWindowChoice.setTitle("Select the window to play with");
        HBox hbox = new HBox();
        //Setting the space between the nodes of a HBox pane
        hbox.setSpacing(10);
/*
        //Setting the margin to the nodes
        hbox.setMargin(textField, new Insets(20, 20, 20, 20));
        hbox.setMargin(playButton, new Insets(20, 20, 20, 20));
        hbox.setMargin(stopButton, new Insets(20, 20, 20, 20));

        //retrieving the observable list of the HBox
        ObservableList list = hbox.getChildren();

        //Adding all the nodes to the observable list (HBox)
        list.addAll(textField, playButton, stopButton);

        Label errorMessage =new Label();
        errorMessage.setText(message);
        Button closeButton =new Button("Ok");

        VBox layoutMessage = new VBox(10);
        layoutMessage.getChildren().addAll(layoutMessage,closeButton);
        layoutMessage.setAlignment(Pos.CENTER);

        Scene boxMessage =new Scene(layoutMessage);

        stageWindowChoice.setScene(boxMessage);
        stageWindowChoice.showAndWait();*/

    }

}
