package it.polimi.se2018.view.gui.gamestage;


import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.*;

import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.stage.ConfirmBox;

import it.polimi.se2018.view.gui.stage.WaitGame;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.util.stream.IntStream;

import static it.polimi.se2018.view.gui.GuiReceiver.getGuiReceiver;

/**
 * Class for handle the Gui gameboard
 */
public class GuiGame implements UIInterface, ViewVisitor {
    private static GuiGame instance;
    private Stage waitStage, gameStage, showCardStage, showRoundStage;


    //variables for show card
    private ShowCardBox cardShow;
    private Boolean clicked;

    public void setTheGameStage() {
        //setTheGame wait
        waitStage = new Stage();
        waitStage.initStyle(StageStyle.UNDECORATED);
        waitStage.initModality(Modality.APPLICATION_MODAL);
        waitStage.setOnCloseRequest(e -> e.consume());

        //setStageForTheGame
        gameStage = new Stage();
        gameStage.initStyle(StageStyle.UTILITY);
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setOnCloseRequest(e -> e.consume());

        //setClass For show a bigger card
        cardShow = new ShowCardBox(700, 600);
        //TODO set up all the stage
        setBoard();
    }

    private GuiGame() {
        //load the dimension of the scene from file

    }

    public static GuiGame getGuiGame() {
        if (instance == null) instance = new GuiGame();
        return instance;
    }

    //variabili per il giocatori
    private int playerId;
    private VBox opposingPlayers; //pane for add/remove Grid of other player
    private VBox centerBox; //conteins the player and dicepool

    private HBox[] boxAllDataPlayer; //contains info and hand
    private VBox[] infoPlayer; //Contains Name, (favorToken, Points in one HBox) and WindowPattern
    private Text[] playersName;
    private VBox[] handOfEachPlayer; //
    private Text[] FavorTokenOfEachPlayer;
    private Text[] PointsOfEachPlayer;

    //fields for windows
    private Text[] nameWindowSNameChoise;
    private Text[] difficultyWindowChoise;
    private GridPane[] windowPatternCardPoolChoise;
    private GridPane[] gridWindowPatternCardPoolChoise;


    private Text[] nameWindowEachPlayer;
    private Text[] difficultyWindowEachPlayer;
    private GridPane[] windowPatternCardOfEachPlayer;
    //fields for cards
    private HBox toolBox; //pane for add/remove the Tool card
    private ImageView[] toolCard; //store the Tool card
    private HBox objectivePublicBox; //pane for add/remove the Public card
    private ImageView[] objectivePublicCard; //store the Public card
    private HBox objectivePrivateBox; //pane for add/remove the Private card
    private ImageView[] objectivePrivateCardOfEachPlayers; //store the Private card

    //round track
    private HBox roundBox;
    private Button[] roundButton;
    private ImageView[][] roundTrackDice;
    private ImageView[] poolDice;

    //Button of the menu game
    private Button[] gameButton;

    public void showWaitStage() {
        waitStage.setResizable(false);
        waitStage.setAlwaysOnTop(true);
        waitStage.centerOnScreen();
        waitStage.setScene(WaitGame.displayMessage());
        waitStage.show();
    }

    private void setBoard() {

        //opposingPlayers
        opposingPlayers = new VBox();
        opposingPlayers.setAlignment(Pos.CENTER_LEFT);
        //centerBox
        centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        //The button for menu in bottom position
        HBox menuButton = new HBox();
        gameButton = new Button[3];
        for (int i = 0; i < 3; i++) {
            gameButton[i] = new Button();
            menuButton.getChildren().add(gameButton[i]);
        }
        gameButton[0].setText("Pesca e inserisci un dado");
        gameButton[0].setOnAction(event -> {
            InsertDiceController packet = new InsertDiceController();
            sendEventToGuiReceiver(packet);
        });
        gameButton[1].setText("Utilizza una Tool Card");
        gameButton[1].setOnAction(event -> {
            IntStream.range(0, toolCard.length).forEach(i -> {
                toolCard[i].setManaged(true);
            });

            UseToolCardController packet = new UseToolCardController();
            sendEventToGuiReceiver(packet);
        });
        gameButton[2].setText("Passa il turno");
        gameButton[2].setOnAction(event -> {
            EndTurnController packet = new EndTurnController();
            sendEventToGuiReceiver(packet);
        });

        //the card in right position
        VBox cardBox = new VBox();
        toolBox = new HBox();
        objectivePublicBox = new HBox();
        objectivePrivateBox = new HBox();
        cardBox.setAlignment(Pos.CENTER_RIGHT);
        cardBox.getChildren().addAll(toolBox, objectivePublicBox, objectivePrivateBox);


        BorderPane pane = new BorderPane(); //keep all the element of the game
        //   pane.setTop();
        pane.setLeft(opposingPlayers);
        pane.setCenter(centerBox);
        pane.setRight(cardBox);
        pane.setBottom(menuButton);

        Scene scene = new Scene(pane, 1000, 800);
        pane.setMinSize(1000, 800);
        gameStage.setTitle("Sagrada a fun game of dice");

        gameStage.setResizable(false);
        gameStage.setAlwaysOnTop(true);
        gameStage.centerOnScreen();
        gameStage.setOnCloseRequest(e -> {
            Boolean result = new ConfirmBox().displayMessage("Sei sicuro di voler abbandonare la partita?");
            if (!result) e.consume();
            //TODO devo informare qualcuno che ho eseguito un logout?
        });
        System.out.println("costruita");
        //setScene
        Platform.runLater(() -> {
            gameStage.setScene(scene);
        });

    }

    private void sendEventToGuiReceiver(EventController packet) {
        packet.setPlayerId(playerId);
        getGuiReceiver().sendEventToNetwork(packet);
    }

    /**
     * this class setUp the
     *
     * @param gameStage
     */

    private void displayBoard(Stage gameStage) {
      /*  this.waitStage = waitStage;



        //set fixed window

        //Call window Box before close the game
        waitStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        Background blackBackground = new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY));
        Background whiteBackground = new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY));
        //setup Window for the game

        //element for the scene for pick the window
        borderPaneRoot.setBackground(blackBackground);
        Scene scene = new Scene(borderPaneRoot, 1000, 800);
        Platform.runLater(()->waitStage.setScene(scene)
        );


        //button for alert window, message from controller
        Button alertWindow = new Button();
        alertWindow.setText("Scegli una window pattern");
        alertWindow.setOnAction(e -> new AlertMessage().displayMessage("test"));
        //button for close window, message
        Button closeWindow = new Button();
        closeWindow.setText("Close Game");
        closeWindow.setOnAction(e -> closeProgram());

        //setUpOf all layout in the gameBoard
        GridPane gridRoundTrack = new GridPane();
        GridPane gridCard = new GridPane();


        //************************ROUNDTRACK*********************************************
        //************************ROUNDTRACK*********************************************
        //************************ROUNDTRACK*********************************************
        //************************ROUNDTRACK*********************************************
        //setup gridRoundTrack
        gridRoundTrack.setPadding(new Insets(10, 10, 10, 10));
        gridRoundTrack.setBackground(blackBackground);
        gridRoundTrack.setVgap(10);
        gridRoundTrack.setHgap(10);
        gridRoundTrack.setAlignment(Pos.CENTER);
        for (int i = 0; i < 10; i++) {

            ButtonDiceRound[i] = new Button();
            gridRoundTrack.add(ButtonDiceRound[i], i, 1);
            ButtonDiceRound[i].setMinSize(60,50);
            ButtonDiceRound[i].setMaxSize(60,50);
            Random rand= new Random();
            for(int n=0; n<9;n++){
                int  value = rand.nextInt(6) + 1;
                int  color = rand.nextInt(5);
                try{
                    Image newImage = new Image(new FileInputStream("src/resources/dadijpg/" + DiceColor.getDiceColor(color)+"Dice"+value+".jpg"));
                    ImageView dice= new ImageView(newImage);
                    dice.setFitHeight(50);
                    dice.setFitWidth(50);
                    dice.setPreserveRatio(true);
                }catch (Exception exception){

                }
            }

        }


        //************************CARD*********************************************
        //************************CARD*********************************************
        //************************CARD*********************************************
        //************************CARD*********************************************


        toolBox.setSpacing(10);
        objectivePublicBox.setSpacing(10);
        objectivePrivateBox.setSpacing(10);
        //SetUp VBox for the Card
        gridCard.setPadding(new Insets(10, 10, 10, 10));
        gridCard.setBackground(blackBackground);
        gridCard.setVgap(10);
        gridCard.setHgap(10);
        gridCard.setAlignment(Pos.TOP_LEFT);
        //Setting the margin to the nodes
        gridCard.add(toolBox, 0, 0);
        gridCard.add(objectivePublicBox, 0, 1);
        gridCard.add(objectivePrivateBox, 0, 2);
        //setUp the button for the private Object
        Image privateCard = null;
        try {
            privateCard = new Image(new FileInputStream("src/resources/carte_jpg/carte_private_retro.jpg"));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        imageViewPrivateCard = new ImageView(privateCard);
        setGraficOfCard(imageViewPrivateCard);
        objectivePrivateBox.getChildren().add(imageViewPrivateCard);
        //setUp the button for the Public Object
        for (int i = 0; i < 3; i++) {
            try {
                Image publicCard = new Image(new FileInputStream("src/resources/carte_jpg/carte_pubbliche_retro.jpg"));
                Image publicToolCard = new Image(new FileInputStream("src/resources/carte_jpg/carte_strumento_retro.jpg"));
                imageViewPublicCard[i] = new ImageView(publicCard);
                objectivePublicBox.getChildren().add(imageViewPublicCard[i]);
                setGraficOfCard(imageViewPublicCard[i]);
                imageViewToolCard[i] = new ImageView(publicToolCard);
                toolBox.getChildren().add(imageViewToolCard[i]);
                setGraficOfCard(imageViewToolCard[i]);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        }
        //SetUp Action for the card
        imageViewPrivateCard.
        imageViewPublicCard[0].setOnMouseClicked(e ->

        {
            cardShow.displayCard(imageViewPublicCard[0], false);
        });
        imageViewPublicCard[1].setOnMouseClicked(e ->

        {
            cardShow.displayCard(imageViewPublicCard[1], false);
        });
        imageViewPublicCard[2].setOnMouseClicked(e ->

        {
            cardShow.displayCard(imageViewPublicCard[2], false);
        });
        imageViewToolCard[0].setOnMouseClicked(e ->

        {
            cardShow.displayCard(imageViewToolCard[0], availableToolCard);
        });
        imageViewToolCard[1].setOnMouseClicked(e ->

        {
            cardShow.displayCard(imageViewToolCard[1], availableToolCard);
            UpdatePublicObject(9, 2);
            UpdateToolCard(7, 2);
        });
        imageViewToolCard[2].setOnMouseClicked(e ->

        {
            cardShow.displayCard(imageViewToolCard[2], availableToolCard);
            UpdatePublicObject(1, 2);
            UpdateToolCard(101, 2);
        });
        //***************************WINDOW PATTERN************************************************
        //***************************WINDOW PATTERN************************************************
        //***************************WINDOW PATTERN************************************************
        //***************************WINDOW PATTERN************************************************
        //***************************WINDOW PATTERN************************************************


        //Setup window Player

        for (int i = 0; i < 4; i++) {


            idWindow[i] = new Label("Id null");
            nameWindow[i] = new Label("Nameless");
            levelWindow[i] = new Label("Level 0");
            idWindow[i].setAlignment(Pos.TOP_LEFT);
            nameWindow[i].setAlignment(Pos.TOP_CENTER);
            levelWindow[i].setAlignment(Pos.TOP_RIGHT);
            cellWindow[i] = new GridPane();
            cellWindow[i].setPadding(new Insets(10, 10, 10, 10));
            cellWindow[i].setVgap(5);
            cellWindow[i].setHgap(5);
            layoutWindow[i] = new VBox();
            HBox infoPlayer = new HBox();
            infoPlayer.setMargin(idWindow[i], new Insets(0, 20, 0, 20));
            infoPlayer.setMargin(nameWindow[i], new Insets(0, 20, 0, 20));
            infoPlayer.setMargin(levelWindow[i], new Insets(0, 20, 0, 20));
            infoPlayer.getChildren().addAll(idWindow[i], nameWindow[i], levelWindow[i]);
            infoPlayer.setAlignment(Pos.CENTER);
            layoutWindow[i].getChildren().addAll(infoPlayer, cellWindow[i]);
            layoutWindow[i].setBackground(whiteBackground);
            gridAllPlayer.add(layoutWindow[i], (i / 2), (i % 2));
            System.out.println("ciao row " + (i / 2) + " column " + (i % 2));
            try {
                for (int row = 0; row < 4; row++) {
                    for (int column = 0; column < 5; column++) {
                        Image cell = new Image(new FileInputStream("src/resources/dadijpg/Dice0.jpg"));
                        imageViewCell[i][row][column] =new ImageView(cell);
                        cellWindow[i].add(imageViewCell[i][row][column], column, row);
                        imageViewCell[i][row][column].setFitHeight(50);
                        imageViewCell[i][row][column].setFitWidth(50);
                        imageViewCell[i][row][column].setPreserveRatio(true);

                    }
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        }
        activeWindow(0);

        //*************************************SetUPPage***********************************************
        //*************************************SetUPPage***********************************************
        //*************************************SetUPPage***********************************************
        //*************************************SetUPPage***********************************************
        borderPaneRoot.setTop(gridRoundTrack);
        borderPaneRoot.setCenter(gridAllPlayer);
        borderPaneRoot.setRight(gridCard);
        Platform.runLater(()->waitStage.show());*/
    }
/*
    private void closeProgram() {
        Boolean result = new ConfirmBox().displayMessage("Sei sicuro di voler uscire dal gioco?");
        if (result) waitStage.close();
    }



    public void UpdatePrivateObject(int idPrivate) {
        try {
            Image newImage;
            if (idPrivate < 0 || idPrivate > 4) {
                newImage = new Image(new FileInputStream("src/main/java/it/polimi/se2018/resources/carte_jpg/carte_private_retro.jpg"));
                System.err.println("Carta pubblica non prevista dal gioco base, necessario un update della GuiGame");
                new AlertMessage().displayMessage("Aggiornare la cartella resources o passare alla versione CLI");
            } else
                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_private_" + idPrivate + ".jpg"));
            imageViewPrivateCard.setImage(newImage);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            new AlertMessage().displayMessage("Sono state eliminate/corrotte delle carte. Controllare la cartella resources, reinstallare il gioco o in alternativa utilizzare la CLI");
        }
    }


    public void UpdatePublicObject(int idPublic, int indexPublic) {
        if (indexPublic < 0 || indexPublic > 2) {
            System.err.println("Le regole del gioco sono cambiate.... quando e perchè?");
            return;
        }
        try {
            Image newImage;
            if (idPublic < 0 || idPublic > 9) {
                newImage = new Image(new FileInputStream("src/main/java/it/polimi/se2018/resources/carte_jpg/carte_pubbliche_retro.jpg"));
                System.err.println("Carta pubblica non prevista dal gioco base, necessario un update della GuiGame");
                new AlertMessage().displayMessage("Aggiornare la cartella resources o passare alla versione CLI");
            } else {

                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_pubbliche_" + idPublic + ".jpg"));

                imageViewPublicCard[indexPublic].setImage(newImage);
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            new AlertMessage().displayMessage("Sono state eliminate/corrotte delle carte. Controllare la cartella resources, reinstallare il gioco o in alternativa utilizzare la CLI");
        }
    }

    public void UpdateToolCard(int idToolCard, int indexToolCard) {
        if (indexToolCard < 0 || indexToolCard > 2) {
            System.err.println("Le regole del gioco sono cambiate.... quando e perchè?");
            return;
        }
        try {
            Image newImage;
            if (idToolCard < 0 || idToolCard > 9) {
                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_strumento_retro.jpg"));
                System.err.println("Carta pubblica non prevista dal gioco base, necessario un update della GuiGame per leggere la carta");
                new AlertMessage().displayMessage("Aggiornare la cartella resources o passare alla versione CLI");
            } else
                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_strumento_" + idToolCard + ".jpg"));
            imageViewToolCard[indexToolCard].setImage(newImage);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            new AlertMessage().displayMessage("Sono state eliminate/corrotte delle carte. Controllare la cartella resources, reinstallare il gioco o in alternativa utilizzare la CLI");
        }
    }

    public void UpdateCellWindow(int indexWindow, int row, int column, int value, DiceColor color) {

    }

    public void activeWindow(int indexWindow) {
        for (int row = 0; row < imageViewCell[indexWindow].length; row++) {
            for (int column = 0; column < imageViewCell[indexWindow][row].length; column++) {
                activeCell(indexWindow, row, column);
            }
        }

    }

    public void activeCell(int indexWindow, int indexRow, int indexColumn)  {
        imageViewCell[indexWindow][indexRow][indexColumn].setOnMouseClicked(e -> {
            System.out.println("è stata cliccata la window: " + indexWindow + " row: " + indexRow + " column " + indexColumn);
            Random rand = new Random();
            int  value = rand.nextInt(6) + 1;
            int  color = rand.nextInt(5);
            try{
                Image newImage = new Image(new FileInputStream("src/resources/dadijpg/" + DiceColor.getDiceColor(color)+"Dice"+value+".jpg"));
                imageViewCell[indexWindow][indexRow][indexColumn].setImage(newImage);
            }catch (Exception exception){

            }
            System.out.println("src/resources/dadi/"+DiceColor.getDiceColor(color)+"Dice"+value+".jpg");
        });

    }*/

    private ImageView createNewImageViewForCard() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    @Override
    public void visit(EndGame event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(StartGame event) {
        System.out.println("viene accettato :" + event.toString());

        int numberOfPlayer = event.getPlayersName().length;
        playerId = event.getPlayerId();
        boxAllDataPlayer = new HBox[numberOfPlayer];
        infoPlayer = new VBox[numberOfPlayer];
        playersName = new Text[numberOfPlayer];
        //setUpAllThe boxes
        for (int i = 0; i < numberOfPlayer; i++) {
            playersName[i] = new Text(event.getPlayersName(i));
            infoPlayer[i] = new VBox(playersName[i]);
            boxAllDataPlayer[i] = new HBox(infoPlayer[i]);
            if (i != playerId) opposingPlayers.getChildren().add(boxAllDataPlayer[i]);
            else centerBox.getChildren().add(boxAllDataPlayer[i]);
        }
        windowPatternCardOfEachPlayer = new GridPane[playersName.length];
        objectivePrivateCardOfEachPlayers = new ImageView[playersName.length];
        handOfEachPlayer = new VBox[event.getPlayersName().length];
        FavorTokenOfEachPlayer = new Text[playersName.length];
        PointsOfEachPlayer = new Text[playersName.length];

        Platform.runLater(() -> {
            gameStage.show();
            waitStage.close();
        });
    }

    @Override
    public void visit(JoinGame event) {
        System.out.println("viene accettato :" + event.toString());
        Platform.runLater(() -> gameStage.show());
    }

    @Override
    public void visit(StartPlayerTurn event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(WaitYourTurn event) {
        //TODO disattivare tutti i bottoni per inviare i pacchetti
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(ShowAllCards event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(SelectCellOfWindowView event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(SelectDiceFromDraftpool event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(SelectToolCard event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(ShowErrorMessage event) {
        System.out.println("viene accettato :" + event.toString());
    }

    //************************************* UPLOAD FROM MODEL *********************************************************************
    //************************************* UPLOAD FROM MODEL *********************************************************************
    //************************************* UPLOAD FROM MODEL *********************************************************************
    //************************************* UPLOAD FROM MODEL *********************************************************************
    @Override
    public void visit(UpdateSinglePrivateObject event) {
        if (objectivePrivateCardOfEachPlayers[event.getIndexPlayer()] == null) {
            objectivePrivateCardOfEachPlayers[event.getIndexPlayer()] = createNewImageViewForCard();
            Platform.runLater(() -> {
                        objectivePrivateBox.getChildren().add(objectivePrivateCardOfEachPlayers[event.getIndexPlayer()]);
                        Image newImage = new Image("file:src/resources/carte_jpg/carte_private_" + event.getPrivateCard().getId() + ".jpg");
                        objectivePrivateCardOfEachPlayers[event.getIndexPlayer()].setImage(newImage);
                        objectivePrivateCardOfEachPlayers[event.getIndexPlayer()].setOnMouseClicked(e -> {
                                    cardShow.displayCard(objectivePrivateCardOfEachPlayers[event.getIndexPlayer()], false);
                                 /*   Boolean clicked = cardShow.displayCard(objectivePrivateCardOfEachPlayers[event.getIndexPlayer()], false)
                                    if (clicked){
                                        // inviare richiesta calcolo punti
                        }*/
                                }

                        );
                    }
            );
        } else {
            System.err.println("è stata già inviata al private card");
        }
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(UpdateAllToolCard event) {
        int numberToolcard = event.getToolCard().length;
        toolCard = new ImageView[numberToolcard];
        //ForEach made for enable the click
        for (int i = 0; i < numberToolcard; i++) {
            toolCard[i] = createNewImageViewForCard();
        }
        Platform.runLater(() -> {
                    IntStream.range(0, numberToolcard).forEach(i -> {
                        toolBox.getChildren().add(toolCard[i]);
                        Image newImage = new Image("file:src/resources/carte_jpg/carte_strumento_" + event.getToolCard(i).getId() + ".jpg");
                        toolCard[i].setImage(newImage);
                        toolCard[i].setOnMouseClicked(e -> {
                            cardShow.displayCard(toolCard[i], true);
                        });
                    });
                }
        );

        System.out.println("viene accettato :" + event.toString());
    }
    @Override
    public void visit(UpdateAllPublicObject event) {
        int numberObjective = event.getPublicCards().length;
        objectivePublicCard = new ImageView[numberObjective];
        //ForEach made for enable the click
        for (int i = 0; i < numberObjective; i++) {
            objectivePublicCard[i] = createNewImageViewForCard();
        }
        Platform.runLater(() -> {
                    IntStream.range(0, numberObjective).forEach(i -> {
                        objectivePublicBox.getChildren().add(objectivePublicCard[i]);
                        Image newImage = new Image("file:src/resources/carte_jpg/carte_pubbliche_" + event.getPublicCards(i).getId() + ".jpg");
                        objectivePublicCard[i].setImage(newImage);
                        objectivePublicCard[i].setOnMouseClicked(e -> {
                            cardShow.displayCard(objectivePublicCard[i], true);
                        });
                    });
                }
        );
        System.out.println("viene accettato :" + event.toString());
    }
    @Override
    public void visit(UpdateSingleToolCardCost event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(UpdateDicePool event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(InitialWindowPatternCard event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(UpdateSinglePlayerHand event) {
        System.out.println("viene accettato :" + event.toString());
    }



    @Override
    public void visit(UpdateSingleCell event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(UpdateSinglePlayerTokenAndPoints event) {
        System.out.println("viene accettato :" + event.toString());
    }


    @Override
    public void visit(UpdateSingleTurnRoundTrack event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(UpdateSingleWindow event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(UpdateInitialWindowPatternCard event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void visit(UpdateInitDimRound event) {
        System.out.println("viene accettato :" + event.toString());
    }

    @Override
    public void showMessage(EventView eventView) {

        eventView.accept(this);
    }
}
