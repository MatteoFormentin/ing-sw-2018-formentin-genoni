package it.polimi.se2018.view.gui.gamestage;


import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_controller.InsertDiceController;
import it.polimi.se2018.list_event.event_received_by_view.*;

import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.stage.ConfirmBox;

import it.polimi.se2018.view.gui.stage.WaitGame;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.se2018.view.gui.GuiReceiver.getGuiReceiver;

/**
 * Class not on the javaFX thread. I made this class for keep clean the thread of javaFx
 * and modify the stage without incur in problems with multithreading
 *
 */
public class GuiGame implements UIInterface,ViewVisitor {
    private static GuiGame instance;
    private Stage waitStage,gameStage,showCardStage,showRoundStage;
    private ShowCardBox cardShow;

    public void setTheGameStage() {
        //setTheGame wait
        waitStage = new Stage();
        waitStage.initStyle(StageStyle.UNDECORATED);
        waitStage.initModality(Modality.APPLICATION_MODAL);
        waitStage.setOnCloseRequest(e -> {
            e.consume();
        });

        //setStageForTheGame
        gameStage =new Stage();
        gameStage.initStyle(StageStyle.UTILITY);
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setOnCloseRequest(e -> {

            e.consume();
        });
        //setStage for show a big card
        showCardStage = new Stage();
        showCardStage.initStyle(StageStyle.UNDECORATED);
        showCardStage.initModality(Modality.APPLICATION_MODAL);
        //TODO set up all the stage
    }

    private GuiGame(){
    }
    public static GuiGame getGuiGame(){
        if(instance==null) instance=new GuiGame();
        return instance;
    }
    private Scene boardgame, windowChoice;


    //variabili per il giocatori
    private int playerId;
    private VBox opposingPlayers; //pane for add/remove Grid of other player
    private GridPane[] gridPlayer; //one pane for each player????? not so usefull
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
    private ImageView[] objectiveCard; //store the Public card
    private HBox ObjectivePrivateBox; //pane for add/remove the Private card
    private ImageView[] objectivePrivateCardOfEachPlayers; //store the Private card

    //round track
    private HBox roundBox;
    private Button[] roundButton;
    private ImageView[][] roundTrackDice;
    private ImageView[] poolDice;

    //Button of the menu game
    private Button[] gameButton;

    public void showWaitStage(){
        waitStage.setTitle("Launcher Sagrada");
        waitStage.setResizable(false);
        waitStage.setAlwaysOnTop(true);
        waitStage.centerOnScreen();
        waitStage.setScene(WaitGame.displayMessage());
        waitStage.show();
    }
    private void setBoard(){
        //keep all the button
        HBox menuButton = new HBox();

        //aggiungere Pulsanti
        gameButton = new Button[4];
        for(int i=0;i<4;i++){
            gameButton[i] = new Button();
            menuButton.getChildren().add(gameButton[i]);
        }
        gameButton[0].setText("Pesca e inserisci un dado");
        gameButton[0].setOnAction(event -> {
            System.out.println("ho cliccato il pulsante");
            EventController packet = new InsertDiceController();
            packet.setPlayerId(playerId);
            getGuiReceiver().sendEventToNetwork(packet);
        });
        gameButton[1].setText("Utilizza una Tool Card");
        gameButton[2].setText("Passa il turno");
        gameButton[3].setText("Esci dal gioco");
        //keep all the card
        VBox cardBox = new VBox();


        BorderPane pane = new BorderPane(); //keep all the element of the game
    /*    pane.setTop();
        pane.setCenter();
        pane.setRight();
        pane.setLeft();*/
        pane.setBottom(menuButton);
        Scene scene = new Scene(pane,1000,800);



        //setScene NO INIT

        Platform.runLater(()-> {
            gameStage.setTitle("Sagrada a fun game of dice");

            gameStage.setResizable(false);
            gameStage.setAlwaysOnTop(true);

            gameStage.setOnCloseRequest(e -> {
                Boolean result = new ConfirmBox().displayMessage("Sei sicuro di voler abbandonare la partita?");
                if (!result) e.consume();

            });
            gameStage.setScene(scene);
            gameStage.centerOnScreen();
        });

    }
    /**
     * this class setUp the
     * @param gameStage
     */

    private void displayBoard(Stage gameStage){
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
        imageViewPrivateCard.setOnMouseClicked(e ->

        {
            cardShow.displayCard(imageViewPrivateCard, false);
        });
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

    private void setGraficOfCard(ImageView imageView) {
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
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

    @Override
    public void visit(EndGame event) {

    }

    @Override
    public void visit(StartGame event) {
        int numberOfPlayer = event.getPlayersName().length;
        playerId = event.getPlayerId();
        playersName = new Text[numberOfPlayer];
        for(int i=0; i<numberOfPlayer;i++){
            playersName[i] = new Text(event.getPlayersName(i));
        }
        windowPatternCardOfEachPlayer = new GridPane[playersName.length];
        objectivePrivateCardOfEachPlayers = new ImageView[playersName.length];
        handOfEachPlayer = new VBox[event.getPlayersName().length];
        FavorTokenOfEachPlayer = new Text[playersName.length];
        PointsOfEachPlayer = new Text[playersName.length];
        setBoard();
        Platform.runLater(()-> {
            gameStage.show();
            waitStage.close();
        });
    }

    @Override
    public void visit(JoinGame event) {
        Platform.runLater(()-> gameStage.show());
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

    @Override
    public void showMessage(EventView eventView) {
        System.out.println("viene accettato");
        eventView.accept(this);
    }
}
