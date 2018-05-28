package it.polimi.se2018.view.prova_gui;


import it.polimi.se2018.model.dice.DiceColor;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * stage for show the main game to the player
 *
 * @author Luca Genoni
 */
public class GUI extends Application {
    private Stage primaryStage;
    private Scene boardgame, windowChoice;

    //topLine
    private BorderPane borderPaneRoot = new BorderPane();
    private HBox topLine = new HBox();

    //for the roundTrack

    private Button[] ButtonDiceRound = new Button[10];// dove inserire i dadi avanzati
    private ImageView[][] diceStackRound = new ImageView[10][9];
    //for the Private Object

    private HBox toolBox = new HBox();
    private HBox objectivePublicBox = new HBox();
    private HBox objectivePrivateBox = new HBox();
    private ImageView imageViewPrivateCard;
    private ImageView[] imageViewPublicCard = new ImageView[3], imageViewToolCard = new ImageView[3];
    private boolean availableToolCard;
    //info of the player
    private GridPane gridAllPlayer = new GridPane();
    private Text[] namePlayer = new Text[4];
    private Text[] numberAvailableToken = new Text[4];


    //info of the window pattern
    private Label[] idWindow = new Label[4];
    private Label[] nameWindow = new Label[4];
    private Label[] levelWindow = new Label[4];
    private GridPane[] cellWindow = new GridPane[4];
    private ImageView[][][] imageViewCell = new ImageView[4][4][5];
    private VBox[] layoutWindow = new VBox[4];

    private Button pickwindow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNIFIED);
        //set fixed window

        //Call window Box before close the game
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        Background blackBackground = new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY));
        Background whiteBackground = new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY));
        //setup Window for the game

        //element for the scene for pick the window
        primaryStage.setResizable(false);
        borderPaneRoot.setBackground(blackBackground);
        Scene scene = new Scene(borderPaneRoot, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sagrada a fun game of dice");

        //button for alert window, message from controller
        Button alertWindow = new Button();
        alertWindow.setText("Scegli una window pattern");
        alertWindow.setOnAction(e -> AlertMessage.displayMessage("test"));
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
        Image privateCard = new Image(new FileInputStream("src/resources/carte_jpg/carte_private_retro.jpg"));
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
            ShowCardBox.displayCard(imageViewPrivateCard, false);
        });
        imageViewPublicCard[0].setOnMouseClicked(e ->

        {
            ShowCardBox.displayCard(imageViewPublicCard[0], false);
        });
        imageViewPublicCard[1].setOnMouseClicked(e ->

        {
            ShowCardBox.displayCard(imageViewPublicCard[1], false);
        });
        imageViewPublicCard[2].setOnMouseClicked(e ->

        {
            ShowCardBox.displayCard(imageViewPublicCard[2], false);
        });
        imageViewToolCard[0].setOnMouseClicked(e ->

        {
            ShowCardBox.displayCard(imageViewToolCard[0], availableToolCard);
        });
        imageViewToolCard[1].setOnMouseClicked(e ->

        {
            ShowCardBox.displayCard(imageViewToolCard[1], availableToolCard);
            UpdatePublicObject(9, 2);
            UpdateToolCard(7, 2);
        });
        imageViewToolCard[2].setOnMouseClicked(e ->

        {
            ShowCardBox.displayCard(imageViewToolCard[2], availableToolCard);
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
/*
            idWindow[i] = new Text("Id null");
            nameWindow[i] = new Text("Nameless");
            levelWindow[i] = new Text("Level 0");*/
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
                        Image cell = new Image(new FileInputStream("src/resources/dadijpg/White.jpg"));
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
        primaryStage.show();
    }

    public static void setUpGUI(String[] args) {
      /*  Stage stage = new Stage();
        stage.centerOnScreen();
        stage.alwaysOnTopProperty();*/
        Application.launch(args);
    }

    private void closeProgram() {
        Boolean result = ConfirmBox.displayMessage("Sei sicuro di voler uscire dal gioco?");
        if (result) primaryStage.close();
    }

    /**
     * method for set the same design to all the card
     *
     * @param imageView
     */
    private void setGraficOfCard(ImageView imageView) {
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
    }


    public void UpdatePrivateObject(int idPrivate) {
        try {
            Image newImage;
            if (idPrivate < 0 || idPrivate > 4) {
                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_private_retro.jpg"));
                System.err.println("Carta pubblica non prevista dal gioco base, necessario un update della GUI");
                AlertMessage.displayMessage("Aggiornare la cartella resources o passare alla versione CLI");
            } else
                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_private_" + idPrivate + ".jpg"));
            imageViewPrivateCard.setImage(newImage);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            AlertMessage.displayMessage("Sono state eliminate/corrotte delle carte. Controllare la cartella resources, reinstallare il gioco o in alternativa utilizzare la CLI");
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
                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_pubbliche_retro.jpg"));
                System.err.println("Carta pubblica non prevista dal gioco base, necessario un update della GUI");
                AlertMessage.displayMessage("Aggiornare la cartella resources o passare alla versione CLI");
            } else {

                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_pubbliche_" + idPublic + ".jpg"));
           /* FadeTransition fade = new FadeTransition(Duration.seconds(2), imageViewPublicCard[indexPublic]);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setAutoReverse(true);*/
                imageViewPublicCard[indexPublic].setImage(newImage);
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            AlertMessage.displayMessage("Sono state eliminate/corrotte delle carte. Controllare la cartella resources, reinstallare il gioco o in alternativa utilizzare la CLI");
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
                System.err.println("Carta pubblica non prevista dal gioco base, necessario un update della GUI per leggere la carta");
                AlertMessage.displayMessage("Aggiornare la cartella resources o passare alla versione CLI");
            } else
                newImage = new Image(new FileInputStream("src/resources/carte_jpg/carte_strumento_" + idToolCard + ".jpg"));
            imageViewToolCard[indexToolCard].setImage(newImage);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            AlertMessage.displayMessage("Sono state eliminate/corrotte delle carte. Controllare la cartella resources, reinstallare il gioco o in alternativa utilizzare la CLI");
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

    }
}
