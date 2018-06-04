package it.polimi.se2018.view.gui;

import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.network.client.Client;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.gamestage.GuiGame;
import it.polimi.se2018.view.gui.stage.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GuiReceiver extends Application implements UIInterface,ViewVisitor {
    //fields for the init game
    private static ClientController client = new Client();
    private static BorderPane pane = new BorderPane();
    private Stage primaryStage;//for close the program called from inside the start, don't need to be static
    private boolean connected = false, login = false;

    //variabili per il gioco
    private static int playerId;
    private static Text[] playersName;
    private static ImageView[][][] windowPatternCardOfEachPlayer;
    private static ImageView[] objectivePrivateCardOfEachPlayers;
    private static ImageView[][] handOfEachPlayer;
    private static Text[] FavorTokenOfEachPlayer;
    private static Text[] PointsOfEachPlayer;


    public void setUpGUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        //creating a Group object
        VBox menu = new VBox();
        Scene startMenu = new Scene(menu, 779, 261);
        Scene game = new Scene (pane);
        primaryStage.setScene(startMenu);
        pane.setCenter(new Text("aspetta gli altri giocatori"));
        pane.setMinSize(800,300);
        //design stage
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle("Launcher Sagrada");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        BackgroundImage backgroundImage = new BackgroundImage(new Image("it/polimi/se2018/resources/Immagine.png", 779, 261, true, true),
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
                    new AlertMessage().displayMessage("Sei già collegato al Server");
                } else {
                    login = new Login().display(client);
                    primaryStage.setScene(game);
                }
            } else
                new AlertMessage().displayMessage("Devi prima impostare l'IP del server e la porta a cui ti vuoi collegare");
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

    private void closeProgram() {
        Boolean result = new ConfirmBox().displayMessage("Sei sicuro di voler uscire dal gioco?");
        if (result) primaryStage.close();
    }

    public void showMessage(EventView EventView) {
        EventView.accept(this);
    }

    @Override
    public void visit(EndGame event) {

    }

    @Override
    public void visit(StartGame event) {
        HBox box = new HBox();
        box.getChildren().add(new Text("Questi sono i giocatori connessi :"));
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pane.setMaxSize(1000,800);
                pane.setMinSize(1000,800);
                pane.setCenter(box);
                playerId = event.getPlayerId();
                playersName= new Text[event.getPlayersName().length];
                for(int i=0; i<event.getPlayersName().length;i++){
                    playersName[i] = new Text(event.getPlayersName(i));
                    box.getChildren().add(playersName[i]);
                }

            }
        });
    }

    @Override
    public void visit(StartPlayerTurn event) {

    }

    @Override
    public void visit(WaitYourTurn event) {

    }

    @Override
    public void visit(ShowAllCards event) {

    }

    @Override
    public void visit(SelectCellOfWindowView event) {

    }

    @Override
    public void visit(SelectDiceFromDraftpool event) {

    }

    @Override
    public void visit(SelectToolCard event) {

    }

    @Override
    public void visit(ShowErrorMessage event) {

    }

    @Override
    public void visit(UpdateAllToolCard event) {

    }

    @Override
    public void visit(UpdateSingleToolCardCost event) {

    }

    @Override
    public void visit(UpdateDicePool event) {

    }

    @Override
    public void visit(InitialWindowPatternCard event) {

    }

    @Override
    public void visit(UpdateSinglePlayerHand event) {

    }

    @Override
    public void visit(UpdateAllPublicObject event) {

    }

    @Override
    public void visit(UpdateSingleCell event) {

    }

    @Override
    public void visit(UpdateSinglePlayerTokenAndPoints event) {

    }

    @Override
    public void visit(UpdateSinglePrivateObject event) {

    }

    @Override
    public void visit(UpdateSingleTurnRoundTrack event) {

    }

    @Override
    public void visit(UpdateSingleWindow event) {

    }

    @Override
    public void visit(UpdateInitialWindowPatternCard event) {

    }

    @Override
    public void visit(UpdateInitDimRound event) {

    }
}
