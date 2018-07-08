package it.polimi.se2018.view.gui;

import it.polimi.se2018.view.gui.stage.ConfirmBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Main class for the Gui, where the JavaFx Application start.
 *
 * @author Luca Genoni
 */
public class GuiMain extends Application {
    private Stage stage;
    private ControllerGUI controllerGUI;
    private ConfirmBox exitGame;
    private Stage thisPrimaryStage;

    /**
     * method for start the Application
     */
    public static void launchGui() {
        launch();
    }

    /**
     * The start of the JavaFx thread and set up of the game
     *
     * @param primaryStage received by the Application.launch() method
     * @throws Exception if something goes wrong during the construction of the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        thisPrimaryStage = primaryStage;
        //design menu stage
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle("Launcher Sagrada");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeApplication();
        });
        exitGame = new ConfirmBox("Sei sicuro di voler uscire dal gioco?");

        //TODO creare un substage del primary per mostrare il caricamento in corso, chiudere una volta concluso il caricamento.
        // servono 2 classi 1 per il caricamento e l'altra per lo start attuale del gioco e non fare show sul primary stage
     /* Text loadingText = new Text("Caricamento del gioco in corso");
        Pane loadingPane = new Pane(loadingText);
        Scene sceneLoading= new Scene(loadingPane);
        stage.setScene(sceneLoading);*/


        //caricare tutte le info di gioco prima di mostrarle
        VBox menu = new VBox();
        //carica il backgound della gui
        Scene startMenu;
        boolean flag;
        do {
            try {
                startMenu = new Scene(menu, 779, 261);
                Image home = new Image(new FileInputStream("src/resources/Immagine.jpg"), 779, 261, true,
                        true);
                BackgroundImage backgroundImage = new BackgroundImage(home,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                menu.setBackground(new Background(backgroundImage));
                flag = false;
            } catch (IOException ex) {
                //TODO mettere qui la richiesta di selezionare il percorso della cartella resources
                //input percorso pakage risorse
                  startMenu = new Scene(menu);
                flag = true;
            }
        } while (flag);

        primaryStage.setScene(startMenu);

        controllerGUI = new ControllerGUI(stage);

        // add button to the menu
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(20);
        Button connectionButton = new Button("MultiPlayer Online");
        connectionButton.setOnAction(e -> controllerGUI.intiConnection(primaryStage));
        Button close = new Button();
        close.setText("Exit");
        close.setOnAction(e -> closeApplication());
        menu.getChildren().addAll(connectionButton, close);


        //show the stage after the setup
        primaryStage.show();
        primaryStage.setAlwaysOnTop(false);
    }

    /**
     * Method for close the application
     */
    private void closeApplication() {
        if (exitGame.displayMessage(thisPrimaryStage)) {
            stage.close();
            Platform.exit();
            System.out.println("Sei uscito dal gioco con successo. \n Per fare l'upgrade mettere un do while nel launcher per riproporre la scelta");
        }
    }


}
