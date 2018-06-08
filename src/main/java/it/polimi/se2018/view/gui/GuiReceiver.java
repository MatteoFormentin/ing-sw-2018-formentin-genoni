package it.polimi.se2018.view.gui;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.gamestage.GuiGame;
import it.polimi.se2018.view.gui.stage.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.AllPermission;

import static it.polimi.se2018.view.gui.GuiInstance.getGuiInstance;

public class GuiReceiver extends Application {
    //fields for the init waitStage
    private Stage primaryStage;
    private GuiGame game;
    private BorderPane pane= new BorderPane();
    private boolean connected = false, login = false;

    public static void launchGui(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage=primaryStage;
        //creating a Group object
        VBox menu = new VBox();
        Scene startMenu = new Scene(menu, 779, 261);
        primaryStage.setScene(startMenu);
        //design menu stage
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle("Launcher Sagrada");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:src/resources/Immagine.jpg", 779, 261, true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        menu.setBackground(new Background(backgroundImage));
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(20);

        // add button to the menu
        Button playButton = new Button("Fai il Login e inizia una partita");
        playButton.setOnAction(e -> {
            WaitGame wait = new WaitGame(primaryStage);
           // game = new GuiGame(primaryStage);
            game.setGameStage(wait);
            if (connected) {
                if (login) {
                    new AlertMessage(primaryStage).displayMessage("Devi effettuare il Relogin (non è vero sei ancora collegato al server, ma devo ancora vederla bene");
                } else {
                    login = new Login(primaryStage).display(getGuiInstance().getClient());
                    if(login) {
                        wait.displayMessage();
                    }
                }
            } else
                new AlertMessage(primaryStage).displayMessage("Devi prima impostare l'IP del server e la porta a cui ti vuoi collegare");
        });
        Button connectionButton = new Button("Impostazioni di rete");
        connectionButton.setOnAction(e -> {
            connected = new SetUpConnection(primaryStage).display(getGuiInstance().getClient());
            login = false;
        });
        Button close = new Button();
        close.setText("Esci Dal Gioco");
        close.setOnAction(e -> closeProgram());

        menu.getChildren().addAll(playButton, connectionButton, close);

        //show the stage after the setup
        primaryStage.show();
        primaryStage.setAlwaysOnTop(false);
    }

    public void closeProgram() {
        Boolean result = new ConfirmBox(primaryStage).displayMessage("Sei sicuro di voler uscire dal gioco?");
        if (result) {
            primaryStage.close();
            System.exit(0);
        }
    }
}
