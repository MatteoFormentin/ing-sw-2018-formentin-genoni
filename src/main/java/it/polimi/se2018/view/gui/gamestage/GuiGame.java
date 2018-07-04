package it.polimi.se2018.view.gui.gamestage;

import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup.*;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.stage.AlertMessage;
import it.polimi.se2018.view.gui.stage.WaitGame;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.IntStream;

import static it.polimi.se2018.view.gui.GuiInstance.getGuiInstance;
import static it.polimi.se2018.view.gui.GuiMain.closeProgram;

/**
 * Class for handle the Gui gameboard
 *
 * @author Luca Genoni
 */
public class GuiGame implements UIInterface, ViewVisitor, ViewModelVisitor, ViewControllerVisitor {

    @Override
    public void errPrintln(String error){
        System.err.println();
        System.err.println(error);
        System.err.println();
    }

    private static GuiGame instance;
    private WaitGame waitGame;
    private Stage gameStage, utilStage,toolStage;
    private Scene sceneGame, sceneInit;

    private static String diceSource = "file:src/resources/dadijpg/";
    private static String toolCardSource = "file:src/resources/carte_jpg/carte_strumento_";
    private static String privateObjectSource = "file:src/resources/carte_jpg/carte_private_";
    private static String publicObjectSource = "file:src/resources/carte_jpg/carte_pubbliche_";
    //variables for show card
    private ShowCardBox cardShow;
    private ShowValue value;
    private AlertMessage popUpGame, popUpWait;
    private boolean init;


    //variabili per il giocatori
    private int playerId;
    private VBox opposingPlayers; //pane for add/remove Grid of other player
    private VBox centerBox; //contains the player and dice pool

    private HBox[] boxAllDataPlayer; //contains info and hand
    private VBox[] infoPlayer; //Contains Name, (favorToken, Points in one HBox) and WindowPattern

    private Text[] playersName;
    private HBox[] numberData;
    private Text[] favorTokenOfEachPlayer;
    private Text[] pointsOfEachPlayer;
    private VBox[] vBoxesHandOfEachPlayer; //
    private ImageView[][] imageViewsHandPlayer;
    //imageView for Hand

    //widnwo of each player
    private VBox[] boxWindowPlayer;
    private Text[] nameWindowPlayer;
    private GridPane[] gridCellPlayer;
    private ImageView[][][] imageViewsCellPlayer;
    private Text[] difficultyWindowPlayer;

    //fields for windows of the players


    //fields for windows pool Choice
    private HBox boxAllWindowPoolChoice;
    private VBox[] boxWindowPoolChoice;
    private Text[] nameWindowPoolChoice;
    private GridPane[] gridCellPoolChoice;
    private ImageView[][][] imageViewsCellPoolChoice;
    private Text[] difficultyWindowPoolChoice;


    //fields for cards
    private VBox cardBox;

    private HBox toolBox; //pane for add/remove the Tool card
    private VBox[] toolCardInfoBox;
    private Text[] costToolCard;
    private ImageView[] toolCard; //store the Tool card
    private HBox objectivePublicBox; //pane for add/remove the Public card
    private ImageView[] objectivePublicCard; //store the Public card
    private HBox objectivePrivateBox; //pane for add/remove the Private card
    private ImageView[] objectivePrivateCardOfEachPlayers; //store the Private card

    //round track
    private HBox boxAllRound;
    private VBox[] boxSingleRound;
    private Text[] textSingleRound;
    private Text currentRound;
    private Text currentTurn;
    private ComboBox<Image>[] comboBoxSingleRound;
    private FlowPane flowPaneDicePool;
    private LinkedList<ImageView> dicePool;

    private Button[] gameButton;

    private GuiGame() {
        toolBox = new HBox();
        toolBox.setSpacing(5);
        objectivePublicBox = new HBox();
        objectivePublicBox.setSpacing(5);
        objectivePrivateBox = new HBox();
        objectivePrivateBox.setSpacing(5);
    }

    public static GuiGame getGuiGame() {
        return instance;
    }

    public static GuiGame createGuiGame() {
        if (instance == null) instance = new GuiGame();
        return instance;
    }

    public void setGameWait(Stage owner) {
        waitGame = new WaitGame(owner);
        waitGame.displayMessage("Ti sei aggiunto ad una partita\nAspetta che la stanza sia pronta");
        closeGame(waitGame.getStage());
        gameStage = new Stage();
        gameStage.initStyle(StageStyle.UTILITY);
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.initOwner(owner);
        closeGame(gameStage);
        utilStage = new Stage();
        utilStage.initStyle(StageStyle.UTILITY);
        utilStage.initModality(Modality.APPLICATION_MODAL);
        utilStage.initOwner(owner);
        closeGame(utilStage);

        //utils for tool card
        toolStage = new Stage(StageStyle.TRANSPARENT);
        toolStage.initModality(Modality.APPLICATION_MODAL);
        toolStage.initOwner(gameStage);
        value = new ShowValue(toolStage);
        //setClass For show a bigger card
        cardShow = new ShowCardBox(gameStage, 700, 600);
        popUpGame = new AlertMessage(gameStage);
        popUpWait = new AlertMessage(utilStage);
        setInit();
        setBoard();
    }

    private void closeGame(Stage stage) {
        stage.setOnCloseRequest(e -> {
            closeProgram();/*
            Boolean result = new ConfirmBox(gameStage).displayMessage("Sei sicuro di voler abbandonare la partita?");
            if (result) {

            } else e.consume();*/
        });
    }


    private void setInit() {
        utilStage.setTitle("Pick a Window");
        HBox allCards = new HBox();
        allCards.setAlignment(Pos.TOP_CENTER);
        allCards.setSpacing(10);
        allCards.getChildren().addAll(toolBox, objectivePublicBox, objectivePrivateBox);
        boxAllWindowPoolChoice = new HBox();
        boxAllWindowPoolChoice.setSpacing(20);
        boxAllWindowPoolChoice.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(allCards, boxAllWindowPoolChoice);
        Scene scene = new Scene(pane, 800, 500);
        utilStage.setScene(scene);

    }

    private void setBoard() {
        //top pane
        boxAllRound = new HBox();
        boxAllRound.setAlignment(Pos.CENTER);
        boxAllRound.setSpacing(5);

        currentRound= new Text("Giro: 1");
        currentTurn= new Text("Turno: 1");
        VBox infoGame = new VBox(currentRound,currentTurn);
        infoGame.setAlignment(Pos.CENTER);
        infoGame.setSpacing(5);
        boxAllRound.getChildren().add(infoGame);
        //opposingPlayers
        opposingPlayers = new VBox();
        opposingPlayers.setAlignment(Pos.CENTER_LEFT);
        //centerBox
        centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        //The button for menu in bottom position
        //Button of the menu game
        HBox menuButton = new HBox();
        menuButton.setAlignment(Pos.BASELINE_RIGHT);
        gameButton = new Button[3];
        for (int i = 0; i < gameButton.length; i++) {
            gameButton[i] = new Button();
            menuButton.getChildren().add(gameButton[i]);

        }
        gameButton[0].setText("Pesca e inserisci un dado");
        gameButton[1].setText("Utilizza una carta strumento");
        gameButton[2].setText("Passa il turno");
        //the card in right position
        cardBox = new VBox();
        cardBox.setAlignment(Pos.CENTER_RIGHT);
        //setUp BorderPane
        BorderPane pane = new BorderPane(); //keep all the element of the game
        pane.setTop(boxAllRound);
        pane.setLeft(opposingPlayers);
        pane.setCenter(centerBox);
        pane.setRight(cardBox);
        pane.setBottom(menuButton);

        sceneGame = new Scene(pane);
        gameStage.setScene(sceneGame);
        gameStage.setTitle("Sagrada a fun game of dice");
        gameStage.setResizable(false);
    }

    private void sendEventAndSetTheId(EventController packet) {
        packet.setPlayerId(playerId);
        getGuiInstance().sendEventToNetwork(packet);
    }

    private ImageView createNewImageViewForCard() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private ImageView createNewImageViewForDicePool() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    //*************************************************VISITOR PATTERN*********************************************************************************
    //*************************************************VISITOR PATTERN*********************************************************************************
    //*************************************************VISITOR PATTERN*********************************************************************************
    //*************************************************VISITOR PATTERN*********************************************************************************
    //*************************************************VISITOR PATTERN*********************************************************************************
    //*************************************************VISITOR PATTERN*********************************************************************************
    //*************************************************VISITOR PATTERN*********************************************************************************
    //*************************************************VISITOR PATTERN**********************************************************************************

    @Override
    public void showEventView(EventView eventView) {
        Platform.runLater(() -> eventView.acceptGeneric(this));
    }

    @Override
    public void restartConnection(String cause) {
        System.out.println("Connessione persa");
        Platform.runLater(() -> {
            if (utilStage.isShowing()) new AlertMessage(utilStage).displayMessage(cause);
            else new AlertMessage(gameStage).displayMessage(cause);
            gameStage.close();
        });
    }

    @Override
    public void visit(EventViewFromController event) {
        System.out.println("Arrivato il pacchetto: "+event+"\n");
        event.acceptControllerEvent(this);
    }

    @Override
    public void visit(EventViewFromModel event) {
        System.out.println("Arrivato il pacchetto: "+event+"\n");
        event.acceptModelEvent(this);
    }


    @Override
    public void visit(UpdateDisconnection event) {
        //TODO implementare
    }

    @Override
    public void visit(UpdatePlayerConnection event) {
        //TODO implementare
    }
    //*************************************************From Controller*********************************************************************************
    //*************************************************From Controller*********************************************************************************
    //*************************************************From Controller*********************************************************************************
    //*************************************************From Controller*********************************************************************************

    @Override
    public void visit(EndGame event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(MoveTimeoutExpired event) {
        IntStream.range(0, gameButton.length).forEach(i -> gameButton[i].setOnAction(null));
        //disattiva dicepool, windowPattern, roundtrack, toolcard
        disableDiceOfDicePool();
        disableWindow(playerId);
        disableAllRound();
        if(value.isDisplaying()) toolStage.close();
        new AlertMessage(gameStage).displayMessage("Hai finito il tempo a disposizione");
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * to activate the button of the game for the player
     *
     * @param event StartPlayerTurn
     */
    @Override
    public void visit(StartGame event) {
        System.err.println("viene accettato :" + event.toString());

    }

   /* @Override
    public void visit(JoinGame event) {
        //TODO aggiungere cose speciale del join game
        System.err.println("è arrivato il pacchetto join, non l'ho mai visto prima d'ora viene accettato :" + event.toString());
        gameStage.show();
    }*/

    /**
     * Method of the Visitor Pattern, event received from the controller
     * to activate the button of the game for the player
     *
     * @param event StartPlayerTurn
     */
    @Override
    public void visit(StartPlayerTurn event) {
        IntStream.range(0, comboBoxSingleRound.length).forEach(i ->comboBoxSingleRound[i].getSelectionModel().clearSelection());
        for (Text t : playersName) t.setFill(Color.BLACK);
        playersName[playerId].setFill(Color.RED);
        gameButton[0].setOnAction(e -> {
            ControllerMoveDrawAndPlaceDie packet = new ControllerMoveDrawAndPlaceDie();
            sendEventAndSetTheId(packet);
        });
        gameButton[1].setOnAction(e -> {
            ControllerMoveUseToolCard packet = new ControllerMoveUseToolCard();
            sendEventAndSetTheId(packet);
        });
        gameButton[2].setOnAction(e -> {
            ControllerEndTurn packet = new ControllerEndTurn();
            sendEventAndSetTheId(packet);
        });
        new AlertMessage(gameStage).displayMessage("è il tuo turno!");

    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * to block the button of the player
     *
     * @param event WaitYourTurn
     */
    @Override
    public void visit(WaitYourTurn event) {
        //TODO disattivare tutti i comandi
        //disattiva il menu
        IntStream.range(0, comboBoxSingleRound.length).forEach(i ->comboBoxSingleRound[i].getSelectionModel().clearSelection());
        IntStream.range(0, gameButton.length).forEach(i -> gameButton[i].setOnAction(null));
        //disattiva dicepool, windowPattern, roundtrack, toolcard
        disableDiceOfDicePool();
        disableWindow(playerId);
        disableAllRound();
        cardShow.setClickIsOn(false);
        for (Text t : playersName) t.setFill(Color.BLACK);
        playersName[event.getIndexCurrentPlayer()].setFill(Color.RED);
    }


    /**
     * Method of the Visitor Pattern, event received from the controller
     * to let the player select a cell of the window
     *
     * @param event SelectCellOfWindow
     */
    @Override
    public void visit(SelectCellOfWindow event) {
        disableDiceOfDicePool();
        activeWindow(playerId);
        disableAllRound();
        cardShow.setClickIsOn(false);
        new AlertMessage(gameStage).displayMessage("Clicca su una cella della tua windowPattern");
    }

    private void activeWindow(int indexWindow) {
        for (int row = 0; row < imageViewsCellPlayer[indexWindow].length; row++) {
            for (int column = 0; column < imageViewsCellPlayer[indexWindow][row].length; column++) {
                activeCell(indexWindow, row, column);
            }
        }
    }

    private void activeCell(int indexWindow, int indexRow, int indexColumn) {
        imageViewsCellPlayer[indexWindow][indexRow][indexColumn].setOnMouseClicked(e -> {
            ControllerInfoEffect packet = new ControllerInfoEffect();
            packet.setPlayerId(playerId);
            int[] info = new int[2];
            info[0] = indexRow;
            info[1] = indexColumn;
            packet.setInfo(info);
            sendEventAndSetTheId(packet);
        });
    }

    private void disableWindow(int indexWindow) {
        for (int row = 0; row < imageViewsCellPlayer[indexWindow].length; row++) {
            for (int column = 0; column < imageViewsCellPlayer[indexWindow][row].length; column++) {
                imageViewsCellPlayer[indexWindow][row][column].setOnMouseClicked(null);
            }
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * to let the player pick a dice from the round pool
     *
     * @param event SelectDiceFromDraftPool
     */
    @Override
    public void visit(SelectDiceFromRoundTrack event) {
        activeRoundTrack();
        disableDiceOfDicePool();
        disableWindow(playerId);
        cardShow.setClickIsOn(false);
        new AlertMessage(gameStage).displayMessage("Seleziona un dado del RoundTrack");
    }



    private void activeRoundTrack() {
        IntStream.range(0, comboBoxSingleRound.length).forEach(i ->comboBoxSingleRound[i].getSelectionModel().clearSelection());
        for (int i = 0; i < comboBoxSingleRound.length; i++) {
            if (!comboBoxSingleRound[i].getItems().isEmpty()) {
                activeSingleRound(i);
            }
        }
    }

    private void activeSingleRound(int indexRound) {
        for (int i = 0; i < comboBoxSingleRound[indexRound].getItems().size(); i++) {
            comboBoxSingleRound[indexRound].setOnAction(e -> {
                disableAllRound();
                ControllerInfoEffect packet = new ControllerInfoEffect();
                packet.setPlayerId(playerId);
                int[] info = new int[2];
                info[0] = indexRound;
                info[1] = comboBoxSingleRound[indexRound].getSelectionModel().getSelectedIndex();
                packet.setInfo(info);
                sendEventAndSetTheId(packet);
            });
        }
    }

    private void disableAllRound() {
        IntStream.range(0, comboBoxSingleRound.length).forEach(i -> comboBoxSingleRound[i].setOnAction(null));
    }

    @Override
    public void visit(SelectValueDice event) {
        int param =value.displayValuePool();
        ControllerInfoEffect packet = new ControllerInfoEffect();
        packet.setPlayerId(playerId);
        int[] info = new int[1];
        info[0] = param;
        packet.setInfo(info);
        sendEventAndSetTheId(packet);
    }

    @Override
    public void visit(SelectIncrementOrDecreaseDice event) {
        int param =value.displayIncreaseDecrease();
        ControllerInfoEffect packet = new ControllerInfoEffect();
        packet.setPlayerId(playerId);
        int[] info = new int[1];
        info[0] = param;
        packet.setInfo(info);
        sendEventAndSetTheId(packet);
    }
    /**
     * Method of the Visitor Pattern, event received from the controller
     * to let the player pick a dice from the draft pool
     *
     * @param event SelectDiceFromDraftPool
     */
    @Override
    public void visit(SelectDiceFromDraftPool event) {
        for (int i = 0; i < dicePool.size(); i++) activeDiceOfDicePool(i);
        disableWindow(playerId);
        disableAllRound();
        cardShow.setClickIsOn(false);
        new AlertMessage(gameStage).displayMessage("Seleziona un dado nella DraftPool");
    }

    private void activeDiceOfDicePool(int index) {
        dicePool.get(index).setOnMouseClicked(e -> {
                    ControllerInfoEffect packet = new ControllerInfoEffect();
                    packet.setPlayerId(playerId);
                    int[] info = new int[1];
                    info[0] = index;
                    packet.setInfo(info);
                    sendEventAndSetTheId(packet);
                }
        );
    }

    private void disableDiceOfDicePool() {
        IntStream.range(0, dicePool.size()).forEach(i -> dicePool.get(i).setOnMouseClicked(null));
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * to let the player pick a tool card
     *
     * @param event SelectToolCard
     */
    @Override
    public void visit(SelectToolCard event) {
        disableDiceOfDicePool();
        disableWindow(playerId);
        disableAllRound();
        cardShow.setClickIsOn(true);
        new AlertMessage(gameStage).displayMessage(
                "Mentre la carta strumento viene mostrata in grande cliccala per utilizzarla");
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * to let the player know that his move wasn't accepted
     *
     * @param event MessageError
     */
    @Override
    public void visit(MessageError event) {
        if (utilStage.isShowing()) new AlertMessage(utilStage).displayMessage(event.getMessage());
        else new AlertMessage(gameStage).displayMessage(event.getMessage());
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * to let the player know that his move was accepted
     *
     * @param event MessageOk
     */
    @Override
    public void visit(MessageOk event) {
        if (utilStage.isShowing()) new AlertMessage(utilStage).displayMessage(event.getMessageConfirm());
        else new AlertMessage(gameStage).displayMessage(event.getMessageConfirm());
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * for show the stage of the init game
     *
     * @param event ShowAllCards
     */
    @Override
    public void visit(ShowAllCards event) {
        waitGame.closeWait();
        utilStage.show();
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * for activated the selection of the window
     *
     * @param event SelectInitialWindowPatternCard
     */
    @Override
    public void visit(SelectInitialWindowPatternCard event) {
        for (int i = 0; i < boxWindowPoolChoice.length; i++) {
            activeWindowChoice(i);
        }
        new AlertMessage(utilStage).displayMessage("Clicca sulla vetrata che vuoi utilizzare durante la partita");
    }

    private void activeWindowChoice(int index) {
        boxWindowPoolChoice[index].setOnMouseClicked(e -> {
            disableWindowChoice();
            ControllerSelectInitialWindowPatternCard packet = new ControllerSelectInitialWindowPatternCard();
            packet.setSelectedIndex(index);
            sendEventAndSetTheId(packet);
        });
    }

    private void disableWindowChoice() {
        IntStream.range(0, boxWindowPoolChoice.length).forEach(i -> boxWindowPoolChoice[i].setOnMouseClicked(null));
    }

    /**
     * Method of the Visitor Pattern, event received from the controller
     * for close the initial and open the start the main game.
     *
     * @param event InitialEnded
     */
    @Override
    public void visit(InitialEnded event) {
        utilStage.close();
        cardBox.getChildren().addAll(toolBox, objectivePublicBox, objectivePrivateBox);
        gameStage.showAndWait();
    }

    //************************************* UPLOAD FROM MODEL ************************************************************************************
    //************************************* UPLOAD FROM MODEL ************************************************************************************
    //************************************* UPLOAD FROM MODEL ************************************************************************************
    //************************************* UPLOAD FROM MODEL ************************************************************************************
    //************************************* UPLOAD FROM MODEL ************************************************************************************
    //************************************* UPLOAD FROM MODEL ************************************************************************************
    //************************************* UPLOAD FROM MODEL ************************************************************************************

    @Override
    public void visit(UpdateNamePlayers event) {
        int numberOfPlayer = event.getPlayerNames().length;
        playerId = event.getPlayerId();
        boxAllDataPlayer = new HBox[numberOfPlayer];
        infoPlayer = new VBox[numberOfPlayer];
        playersName = new Text[numberOfPlayer];
        vBoxesHandOfEachPlayer = new VBox[numberOfPlayer];
        imageViewsHandPlayer = new ImageView[numberOfPlayer][5];
        favorTokenOfEachPlayer = new Text[numberOfPlayer];
        pointsOfEachPlayer = new Text[numberOfPlayer];
        objectivePrivateCardOfEachPlayers = new ImageView[numberOfPlayer];
        //init array for window
        boxWindowPlayer = new VBox[numberOfPlayer];
        nameWindowPlayer = new Text[numberOfPlayer];
        difficultyWindowPlayer = new Text[numberOfPlayer];
        gridCellPlayer = new GridPane[numberOfPlayer];
        imageViewsCellPlayer = new ImageView[numberOfPlayer][][];

        //setUpAllThe boxes
        for (int i = 0; i < numberOfPlayer; i++) {
            //box window
            nameWindowPlayer[i] = new Text("Non scelta");
            difficultyWindowPlayer[i] = new Text("0");
            gridCellPlayer[i] = new GridPane();
            boxWindowPlayer[i] = new VBox(nameWindowPlayer[i], gridCellPlayer[i], difficultyWindowPlayer[i]);

            //box info
            playersName[i] = new Text(event.getNames(i));
            favorTokenOfEachPlayer[i] = new Text("Favor : 0");
            pointsOfEachPlayer[i] = new Text("Points : 0");
            infoPlayer[i] = new VBox(playersName[i], favorTokenOfEachPlayer[i], pointsOfEachPlayer[i], boxWindowPlayer[i]);

            //box player
            vBoxesHandOfEachPlayer[i] = new VBox();
            boxAllDataPlayer[i] = new HBox(infoPlayer[i], vBoxesHandOfEachPlayer[i]);

            //select place
            if (i != playerId) {
                opposingPlayers.getChildren().add(boxAllDataPlayer[i]);
                boxAllDataPlayer[i].setAlignment(Pos.TOP_LEFT);
            } else {
                boxAllDataPlayer[i].setAlignment(Pos.CENTER);
                centerBox.getChildren().add(boxAllDataPlayer[i]);
            }
            for (int j = 0; j < imageViewsHandPlayer[i].length; j++) {
                imageViewsHandPlayer[i][j] = createNewImageViewForDicePool();
                imageViewsHandPlayer[i][j].setImage(null);
                vBoxesHandOfEachPlayer[i].getChildren().add(imageViewsHandPlayer[i][j]);
            }
        }
        flowPaneDicePool = new FlowPane(5, 5);
        flowPaneDicePool.setOrientation(Orientation.HORIZONTAL);
        flowPaneDicePool.setAlignment(Pos.BOTTOM_CENTER);
        dicePool = new LinkedList<>();
        for (int i = 0; i < (2 * numberOfPlayer + 1); i++) {
            dicePool.add(createNewImageViewForDicePool());
            flowPaneDicePool.getChildren().add(dicePool.get(i));
        }
        centerBox.getChildren().add(flowPaneDicePool);
    }
    /**
     * Method of the Visitor Pattern, event received from the model for setUp a private Object
     *
     * @param event UpdateSinglePrivateObject
     */
    @Override
    public void visit(UpdateSinglePrivateObject event) {
        objectivePrivateCardOfEachPlayers[event.getIndexPlayer()] = createNewImageViewForCard();
        objectivePrivateBox.getChildren().add(objectivePrivateCardOfEachPlayers[event.getIndexPlayer()]);
        Image newImage=null;
        try{
            newImage = new Image(new FileInputStream(privateObjectSource + event.getPrivateCard().getId() + ".jpg"));
        }catch(IOException ex){
            System.out.println("can't load");
        }
        objectivePrivateCardOfEachPlayers[event.getIndexPlayer()].setImage(newImage);
        objectivePrivateCardOfEachPlayers[event.getIndexPlayer()].setOnMouseClicked(e ->
                cardShow.displayCard(objectivePrivateCardOfEachPlayers[event.getIndexPlayer()], false)
        );
    }

    /**
     * Method of the Visitor Pattern, event received from the model for setUp the Initial window Pattern
     *
     * @param event UpdateInitialWindowPatternCard
     */
    @Override
    public void visit(UpdateInitialWindowPatternCard event) {
        int numberWindow = event.getInitialWindowPatternCard().length;
        int numberLine = event.getInitialWindowPatternCard(0).getMatrix().length;
        int numberColumn = event.getInitialWindowPatternCard(0).getColumn(0).length;
        boxWindowPoolChoice = new VBox[numberWindow];
        nameWindowPoolChoice = new Text[numberWindow];
        difficultyWindowPoolChoice = new Text[numberWindow];
        gridCellPoolChoice = new GridPane[numberWindow];
        imageViewsCellPoolChoice = new ImageView[numberWindow][numberLine][numberColumn];
        for (int i = 0; i < numberWindow; i++) {
            gridCellPoolChoice[i] = new GridPane();
            gridCellPoolChoice[i].setPadding(new Insets(10, 0, 10, 0));
            gridCellPoolChoice[i].setVgap(5);
            gridCellPoolChoice[i].setHgap(5);
            difficultyWindowPoolChoice[i] = new Text("Livello "+Integer.toString(event.getInitialWindowPatternCard(i).getDifficulty()));
            difficultyWindowPoolChoice[i].setTextAlignment(TextAlignment.RIGHT);
            nameWindowPoolChoice[i] = new Text(event.getInitialWindowPatternCard(i).getName());
            boxWindowPoolChoice[i] = new VBox(nameWindowPoolChoice[i], gridCellPoolChoice[i], difficultyWindowPoolChoice[i]);
            boxAllWindowPoolChoice.getChildren().add(boxWindowPoolChoice[i]);
            for (int line = 0; line < numberLine; line++) {
                for (int column = 0; column < numberColumn; column++) {
                    String color;
                    if (event.getInitialWindowPatternCard(i).getCell(line, column).getColorRestriction() == null) {
                        color = "";
                    } else {
                        color = event.getInitialWindowPatternCard(i).getCell(line, column).getColorRestriction().toString();
                    }
                    Image newImage = new Image(diceSource
                            + color
                            + "Dice" + Integer.toString(event.getInitialWindowPatternCard(i).getCell(line, column).getValueRestriction()) + ".jpg");
                    imageViewsCellPoolChoice[i][line][column] = new ImageView(newImage);
                    imageViewsCellPoolChoice[i][line][column].setFitHeight(25);
                    imageViewsCellPoolChoice[i][line][column].setFitWidth(25);
                    imageViewsCellPoolChoice[i][line][column].setPreserveRatio(true);
                    gridCellPoolChoice[i].add(imageViewsCellPoolChoice[i][line][column], column, line);
                }
            }
        }
    }


    /**
     * Method of the Visitor Pattern, event received from the model for setUp all the tool card
     *
     * @param event UpdateAllToolCard
     */
    @Override
    public void visit(UpdateAllToolCard event) {
        toolCard = new ImageView[event.getToolCard().length];
        costToolCard = new Text[event.getToolCard().length];
        toolCardInfoBox = new VBox[event.getToolCard().length];
        //ForEach made for enable the click
        IntStream.range(0, event.getToolCard().length).forEach(i -> {
            toolCard[i] = createNewImageViewForCard();
            costToolCard[i] = new Text("Costo: "+ event.getToolCard(i).getFavorToken());
            toolCardInfoBox[i] = new VBox(toolCard[i],costToolCard[i]);
            toolBox.getChildren().add(toolCardInfoBox[i]);
            Image newImage = new Image(toolCardSource + event.getToolCard(i).getId() + ".jpg");
            toolCard[i].setImage(newImage);
            toolCard[i].setOnMouseClicked(e -> {
                if (cardShow.displayCard(toolCard[i], true)) {
                    ControllerSelectToolCard packet = new ControllerSelectToolCard();
                    packet.setIndexToolCard(i);
                    sendEventAndSetTheId(packet);
                }
            });
        });
    }

    /**
     * Method of the Visitor Pattern, event received from the model for setUp all the public object
     *
     * @param event UpdateAllPublicObject
     */
    @Override
    public void visit(UpdateAllPublicObject event) {
        //creates the array of public cards
        objectivePublicCard = new ImageView[event.getPublicCards().length];
        //creates each public card
        IntStream.range(0, event.getPublicCards().length).forEach(i -> {
            objectivePublicCard[i] = createNewImageViewForCard();
            objectivePublicBox.getChildren().add(objectivePublicCard[i]);
            Image newImage = new Image(publicObjectSource + event.getPublicCards(i).getId() + ".jpg");
            objectivePublicCard[i].setImage(newImage);
            objectivePublicCard[i].setOnMouseClicked(e ->
                    cardShow.displayCard(objectivePublicCard[i], false)
            );
        });
    }


    /**
     * Method of the Visitor Pattern, event received from the model for setUp the Round Track.
     *
     * @param event UpdateInitDimRound
     */
    @Override
    public void visit(UpdateInitDimRound event) {
        int numberRound = event.getRoundTrack().length;
        boxSingleRound = new VBox[event.getRoundTrack().length];
        textSingleRound = new Text[event.getRoundTrack().length];
        comboBoxSingleRound = new ComboBox[event.getRoundTrack().length];
        for (int i = 0; i < numberRound; i++) {
            comboBoxSingleRound[i] = new ComboBox<>();
            ImageView size = createNewImageViewForDicePool();
            comboBoxSingleRound[i].setMinSize(size.getFitWidth() + 10, size.getFitHeight() + 10);
            comboBoxSingleRound[i].setMaxSize(size.getFitWidth() + 10, size.getFitHeight() + 10);
            // setUP combo box factory, with this method the dice don't disapear when is selected
            comboBoxSingleRound[i].setCellFactory(listView -> new ImageListDice());
            comboBoxSingleRound[i].setButtonCell(new ImageListDice());
            textSingleRound[i] = new Text(Integer.toString(i + 1));
            textSingleRound[i].setTextAlignment(TextAlignment.CENTER);
            boxSingleRound[i] = new VBox(textSingleRound[i], comboBoxSingleRound[i]);
            boxSingleRound[i].setAlignment(Pos.CENTER);
            boxSingleRound[i].setSpacing(5);
            boxAllRound.getChildren().add(boxSingleRound[i]);

            if (event.getRoundTrack(i) != null) {
                for (int j = 0; j < event.getRoundTrack(i).size(); j++) {
                    Image newImage = new Image(diceSource + event.getRoundTrack(i).get(j).getColor()
                            + "Dice" + event.getRoundTrack(i).get(j).getValue() + ".jpg");
                    comboBoxSingleRound[i].getItems().add(newImage);
                }
            }
        }
    }


    //utils class for a better combo box
    private class ImageListDice extends ListCell<Image> {
        private final ImageView image;

        ImageListDice() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            image = createNewImageViewForDicePool();
        }

        @Override
        protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) setGraphic(null);
            else {
                image.setImage(item);
                setGraphic(image);
            }
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the model for set up the window of one player.
     *
     * @param event UpdateSingleWindow
     */
    @Override
    public void visit(UpdateSingleWindow event) {
        int numberLine = event.getWindowPatternCard().getMatrix().length;
        int numberColumn = event.getWindowPatternCard().getColumn(0).length;
        int dimCell;
        if (event.getIndexPlayer() == playerId) dimCell = 50;
        else dimCell = 25;
        imageViewsCellPlayer[event.getIndexPlayer()] = new ImageView[numberLine][numberColumn];
        nameWindowPlayer[event.getIndexPlayer()].setText(event.getWindowPatternCard().getName());
        difficultyWindowPlayer[event.getIndexPlayer()].setText("Livello "+Integer.toString(event.getWindowPatternCard().getDifficulty()));
        difficultyWindowPlayer[event.getIndexPlayer()].setTextAlignment(TextAlignment.RIGHT);
        for (int line = 0; line < numberLine; line++) {
            for (int column = 0; column < numberColumn; column++) {
                String color;
                if (event.getWindowPatternCard().getCell(line, column).getColorRestriction() == null)color = "";
                else color = event.getWindowPatternCard().getCell(line, column).getColorRestriction().toString();
                Image newImage = new Image(diceSource + color + "Dice"
                        + Integer.toString(event.getWindowPatternCard().getCell(line, column).getValueRestriction()) + ".jpg");
                imageViewsCellPlayer[event.getIndexPlayer()][line][column] = new ImageView(newImage);
                imageViewsCellPlayer[event.getIndexPlayer()][line][column].setFitHeight(dimCell);
                imageViewsCellPlayer[event.getIndexPlayer()][line][column].setFitWidth(dimCell);
                imageViewsCellPlayer[event.getIndexPlayer()][line][column].setPreserveRatio(true);
                gridCellPlayer[event.getIndexPlayer()].add(imageViewsCellPlayer[event.getIndexPlayer()][line][column], column, line);
            }
        }
    }

    @Override
    public void visit(UpdateSingleToolCardCost event) {
        costToolCard[event.getIndexToolCard()].setText("Costo: "+event.getCostToolCard());
    }

    /**
     * Method of the Visitor Pattern, event received from the model for update the dicePool
     *
     * @param event UpdateSingleCell
     */
    @Override
    public void visit(UpdateDicePool event) {
        if (event.getDicePool() != null) {
            for (int i = 0; i < dicePool.size(); i++) {
                try {
                    Image newImage = new Image(diceSource + event.getDicePool().getDice(i).getColor()
                            + "Dice" + event.getDicePool().getDice(i).getValue() + ".jpg");
                    dicePool.get(i).setImage(newImage);
                } catch (Exception e) {
                    dicePool.get(i).setImage(null);
                }
            }
        }
    }

    /**
     * Non sono utilizzabili come indici, sono solo info da mostrare
     *
     * @param event
     */
    @Override
    public void visit(UpdateInfoCurrentTurn event) {
        currentRound.setText("Giro: "+event.getCurrentRound());
        currentTurn.setText("Turno: "+event.getCurrentTurn());
    }

    /**
     * Method of the Visitor Pattern, event received from the model for update the hand of one player
     *
     * @param event UpdateSingleCell
     */
    @Override
    public void visit(UpdateSinglePlayerHand event) {
        if (event.getHandPlayer() != null) {
            for (int i = 0; i < imageViewsHandPlayer[event.getIndexPlayer()].length; i++) {
                try {
                    Image newImage = new Image(diceSource + event.getHandPlayer().getDice(i).getColor()
                            + "Dice" + event.getHandPlayer().getDice(i).getValue() + ".jpg");
                    imageViewsHandPlayer[event.getIndexPlayer()][i].setImage(newImage);
                } catch (Exception e) {
                    imageViewsHandPlayer[event.getIndexPlayer()][i].setImage(null);
                }
            }
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the model for update a single cell of the window
     *
     * @param event UpdateSingleCell
     */
    @Override
    public void visit(UpdateSingleCell event) {
        Image newImage;
        if (event.getDice() == null) {
            String color;
            if (event.getColorRestriction() == null) color = "";
            else color = event.getColorRestriction().toString();
            newImage = new Image(diceSource+ color+ "Dice" + Integer.toString(event.getValueRestriction()) + ".jpg");
        } else {
            newImage = new Image(diceSource + event.getDice().getColor()
                    + "Dice" + Integer.toString(event.getDice().getValue()) + ".jpg");
        }
        imageViewsCellPlayer[event.getIndexPlayer()][event.getLine()][event.getColumn()].setImage(newImage);
    }

    /**
     * Method of the Visitor Pattern, event received from the model for update the token and the point of one player.
     *
     * @param event UpdateSinglePlayerTokenAndPoints
     */
    @Override
    public void visit(UpdateSinglePlayerToken event) {
        favorTokenOfEachPlayer[event.getIndexInGame()].setText("Favor : " + event.getFavorToken());
        //TODO removed the points
        //pointsOfEachPlayer[event.getIndexInGame()].setText("Points : " + event.getPoints());
    }

    /**
     * Method of the Visitor Pattern, event received from the model for update a single Turn.
     *
     * @param event UpdateSingleTurnRoundTrack
     */
    @Override
    public void visit(UpdateSingleTurnRoundTrack event) {
        while (!comboBoxSingleRound[event.getIndexRound()].getItems().isEmpty()) {
            comboBoxSingleRound[event.getIndexRound()].getItems().remove(0);
        }
        for (int i = 0; i < event.getRoundDice().size(); i++) {
            Image newImage = new Image(diceSource + event.getRoundDice().get(i).getColor()
                    + "Dice" + event.getRoundDice().get(i).getValue() + ".jpg");
            comboBoxSingleRound[event.getIndexRound()].getItems().add(newImage);
        }
        for (ComboBox<Image> aComboBoxSingleRound : comboBoxSingleRound) {
            aComboBoxSingleRound.getSelectionModel().clearSelection();
        }
    }

    @Override
    public void visit(UpdateCurrentPoint event) {
        //TODO implements the update of the current points
    }

    @Override
    public void visit(UpdateStatPodium event) {
        BorderPane paneShow = new BorderPane();
        VBox paneWin = new VBox();
        GridPane pane = new GridPane();
        paneShow.setTop(new Text("    "));
        paneShow.setCenter(paneWin);
        paneShow.setBottom(new Text("    "));
        paneShow.setRight(new Text("    "));
        paneShow.setLeft(new Text("    "));

        Text win;
        if(playerId==event.getOneSortedPlayerInfo(0,0))
            win = new Text("Hai Vinto!!!!!");
        else win = new Text("Hai Perso!!!!!");
        win.setTextAlignment(TextAlignment.CENTER);

        Button game = new Button("clicca per vedere la gameBoard");
        paneWin.getChildren().addAll(win,pane,game);
        paneWin.setAlignment(Pos.CENTER);
        paneWin.setSpacing(30);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(5);
        pane.setHgap(15);
        //prima riga
        pane.add(new Text("Posizione:"),0,0);
        for(int i=1;i<(event.getSortedPlayer().length+1);i++){
            Text description= new Text(Integer.toString(i));
            pane.add(description,i,0);
        }

        //prima colonna
        for(int currentDescription=0;currentDescription<event.getOneSortedPlayer(0).length;currentDescription++){
            Text description= new Text(event.getDescription(currentDescription));
            pane.add(description,0,currentDescription+1);
        }

        for(int i=0; i<event.getSortedPlayer().length;i++){
            Text name = new Text(playersName[event.getOneSortedPlayerInfo(i,0)].getText());
            pane.add(name,(i+1),1);
            for(int j=1; j<event.getOneSortedPlayer(i).length;j++){
                Text point = new Text(""+ event.getOneSortedPlayerInfo(i,j));
                pane.add(point,(i+1),j+1);
            }
        }
        Scene scenePoint = new Scene(paneShow);
        gameStage.setOnCloseRequest(e-> gameStage.close());
        gameStage.close();
        utilStage.setScene(scenePoint);
        game.setOnAction(e-> utilStage.setScene(sceneGame));
        utilStage.show();
    }

}