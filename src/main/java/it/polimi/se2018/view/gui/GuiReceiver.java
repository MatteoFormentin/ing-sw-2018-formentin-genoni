package it.polimi.se2018.view.gui;

import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.network.client.Client;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.stage.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GuiReceiver extends Application implements UIInterface,ViewVisitor {
    //fields for the init waitScene
    private static ClientController client = new Client();
    private static BorderPane pane = new BorderPane();
    private static Stage primaryStage,secondStage,gameStage;//for close the program called from inside the start, don't need to be static
    private boolean connected = false, login = false;

    //variabili per il giocatori
    private static int playerId;
    private static Text[] playersName;
    private static ImageView[][] handOfEachPlayer;
    private static Text[] FavorTokenOfEachPlayer;
    private static Text[] PointsOfEachPlayer;

    //fields for windows
    private static Text[] nameWindowSNameChoise;
    private static Text[] difficultyWindowChoise;
    private static ImageView[][][] windowPatternCardPoolChoise;
    private static GridPane[] gridWindowPatternCardPoolChoise;


    private static Text[] nameWindowEachPlayer;
    private static Text[] difficultyWindowEachPlayer;
    private static ImageView[][][] windowPatternCardOfEachPlayer;
    //fields for cards
    private static ImageView[] toolCard;
    private static ImageView[] objectiveCard;
    private static ImageView[] objectivePrivateCardOfEachPlayers;

    //round track
    private static ImageView[][] roundTrack;
    private static ImageView[] poolDice;

    public void setUpGUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        //creating a Group object
        VBox menu = new VBox();
        Scene startMenu = new Scene(menu, 779, 261);
        Scene waitScene = new Scene (pane,300,200);
        primaryStage.setScene(startMenu);
        pane.setCenter(new Text("aspetta gli altri giocatori"));
        waitScene.setCursor(Cursor.WAIT);
        //design stage
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
        secondStage = new Stage(StageStyle.UNDECORATED);
      /*  secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setAlwaysOnTop(true);
        secondStage.centerOnScreen();
        secondStage.setScene(waitScene);
        secondStage.centerOnScreen();
        secondStage.show();*/
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
                    new AlertMessage().displayMessage("Devi effettuare il Relogin(non è vero sei ancora collegato al server, ma non devi rompere)");
                } else {
                    login = new Login().display(client);
                    new WaitGame().displayMessage(secondStage);

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
        System.exit(0);
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
                //stage
                gameStage = new Stage(StageStyle.UNDECORATED);
                gameStage.initModality(Modality.APPLICATION_MODAL);
                gameStage.setAlwaysOnTop(true);
                gameStage.centerOnScreen();

                playerId=event.getPlayerId();
                pane=new BorderPane();
                pane.setCenter(box);
                VBox[] player = new VBox[event.getPlayersName().length];
                Scene gameScene =new Scene(pane,1000,800);
                playersName= new Text[event.getPlayersName().length];
                gridWindowPatternCardPoolChoise = new GridPane[event.getPlayersName().length];
                for(int i=0; i<event.getPlayersName().length;i++){
                    playersName[i] = new Text(event.getPlayersName(i));
                    gridWindowPatternCardPoolChoise[i] = new GridPane();
                    player[i]= new VBox();
                    player[i].setAlignment(Pos.CENTER);
                    player[i].getChildren().add(playersName[i]);
                    player[i].getChildren().add(gridWindowPatternCardPoolChoise[i]);
                    box.getChildren().add(player[i]);
                }
                secondStage.close();
                gameStage.setScene(gameScene);
                gameStage.centerOnScreen();
                gameStage.show();
            }
        });
    }

    @Override
    public void visit(JoinGame event) {

    }

    @Override
    public void visit(InitialWindowPatternCard event) {

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

    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************

    public void visit(UpdateInitialWindowPatternCard event) {
        int number = event.getInitialWindowPatternCard().length;
        int line = event.getInitialWindowPatternCard(0).getMatrix().length;
        int column = event.getInitialWindowPatternCard(0).getColumn(0).length;
        //ini
    }

    public void visit(UpdateAllToolCard event) {
      //TODO  toolCard = event.getToolCard();
    }

    public void visit(UpdateAllPublicObject event) {
        //TODO  objectivePublicCards = event.getPublicCards();
    }

    public void visit(UpdateInitDimRound event) {
        //TODO  roundTrack = event.getRoundTrack();
    }

    public void visit(UpdateSingleToolCardCost event) {
        //TODO toolCard[event.getIndexToolCard()].setFavorToken(event.getCostToolCard());
    }

    public void visit(UpdateDicePool event) {
        //TODO dicePool = event.getDicePool();
    }


    public void visit(UpdateSinglePlayerHand event) {
        //TODO handOfEachPlayer[event.getIndexPlayer()] = event.getHandPlayer();
    }

    public void visit(UpdateSingleCell event) {
        //TODO  windowPatternCardOfEachPlayer[event.getIndexPlayer()].getCell(event.getLine(), event.getColumn()).setDice(event.getDice());
      /*  cliMessage.showOpponentWindow(playersName[event.getIndexPlayer()]);
        cliMessage.showWindowPatternCard(windowPatternCardOfEachPlayer[event.getIndexPlayer()]);*/
    }

    public void visit(UpdateSinglePlayerTokenAndPoints event) {
        //TODO  FavorTokenOfEachPlayer[event.getIndexInGame()] = event.getFavorToken();
      //  PointsOfEachPlayer[event.getIndexInGame()] = event.getPoints();
    }

    public void visit(UpdateSinglePrivateObject event) {
        //TODO objectivePrivateCardOfEachPlayers[event.getIndexPlayer()] = event.getPrivateCard();
    }

    public void visit(UpdateSingleTurnRoundTrack event) {
        //TODO roundTrack[event.getIndexRound()] = event.getDicePool();
    }

    public void visit(UpdateSingleWindow event) {
        //TODO  windowPatternCardOfEachPlayer[event.getIndexPlayer()] = event.getWindowPatternCard();
    }

}
