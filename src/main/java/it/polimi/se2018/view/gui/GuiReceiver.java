package it.polimi.se2018.view.gui;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.stage.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.AllPermission;

import static it.polimi.se2018.view.gui.gamestage.GuiGame.getGuiGame;

public class GuiReceiver implements UIInterface {
    //fields for the init waitStage
    private static GuiReceiver instance;
    private ClientController client;
    private Stage primaryStage;
    private BorderPane pane= new BorderPane();
    private boolean connected = false, login = false;

    private GuiReceiver(){

    }
    public static GuiReceiver getGuiReceiver(){

        if(instance==null) instance=new GuiReceiver();
        return instance;
    }
    public void setUpGUI(Stage primaryStage) {
        getGuiGame();
        this.primaryStage = primaryStage;
    }

    public void start(ClientController client){

        this.client =client;
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

        //design second stage

        getGuiGame().setTheGameStage();
        System.out.println("arrivato al Gui Receiver");
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:src/resources/Immagine.jpg", 779, 261, true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        //design scene wait

        //design scene

        menu.setBackground(new Background(backgroundImage));
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(20);

        // add button to the menu
        Button playButton = new Button("Fai il Login e inizia una partita");
        playButton.setOnAction(e -> {
            if (connected) {
                if (login) {
                    new AlertMessage(primaryStage).displayMessage("Devi effettuare il Relogin(non è vero sei ancora collegato al server, ma non devi rompere)");
                } else {
                    login = new Login().display(client);
                    if(login)getGuiGame().showWaitStage();
                }
            } else
                new AlertMessage(primaryStage).displayMessage("Devi prima impostare l'IP del server e la porta a cui ti vuoi collegare");
        });
        Button connectionButton = new Button("Impostazioni di rete");
        connectionButton.setOnAction(e -> {
            connected = new SetUpConnection().display(client);
            login = false;
        });
        Button close = new Button();
        close.setText("Esci Dal Gioco");
        close.setOnAction(e -> closeProgram());

        menu.getChildren().addAll(playButton, connectionButton, close);

        //show the stage after the setup
        primaryStage.show();
    }

    public void closeProgram() {
        Boolean result = new ConfirmBox().displayMessage("Sei sicuro di voler uscire dal gioco?");
        if (result) {
            primaryStage.close();
            System.exit(0);
        }

    }

    /**
     * inoltrare il messaggio alla classe adibita al gioco vero e proprio
     *
     * @param eventView
     */
    public void showMessage(EventView eventView) {
        try {
            getGuiGame().showMessage(eventView);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * inoltrare al client un pacchetto
     *
     * @param event
     */
    public void sendEventToNetwork(EventController event){
        client.sendEventToController(event);
    }
}
