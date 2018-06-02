package it.polimi.se2018.view.prova_gui;

import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.prova_gui.Stage.SetUpConnection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.AllPermission;

public class GuiReceiver extends Application implements UIInterface, ViewVisitor {
    private ClientController client;
    private int playerId;
    private Stage primaryStage;

    public void setClient(ClientController client) {
        this.client = client;
    }

    public static void setUpGUI(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //creating a Group object
        this.primaryStage= primaryStage;
        VBox menu =new VBox();
        Scene scene = new Scene(menu,779 ,261);
        primaryStage.setScene(scene);

        //design stage
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle("Launcher Sagrada");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();

        });

        //design scene
        BackgroundImage backgroundImage= new BackgroundImage(new Image("it/polimi/se2018/resources/Immagine.png",779 ,261,true,true),
                    BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);

        menu.setBackground(new Background(backgroundImage));
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(20);


        // add button to the menu
        Button playButton = new Button("Entra in una Lobby");
        playButton.setOnAction(e-> AlertMessage.displayMessage("Sei entrato nella lobby"));

        Button connectionButton = new Button("Collegati al Server");
        connectionButton.setOnAction(e-> AlertMessage.displayMessage("Stage Connessione"));

        Button stopButton = new Button("Esci Dal Gioco");
        stopButton.setOnAction(e -> closeProgram());

        menu.getChildren().addAll(playButton,connectionButton,stopButton);

        //show the stage after the setup
        primaryStage.show();
    }
    private void closeProgram() {
        Boolean result = ConfirmBox.displayMessage("Sei sicuro di voler uscire dal gioco?");
        if (result) primaryStage.close();
    }

    @Override
    public void showMessage(EventView EventView) {
        EventView.accept(this);
    }

    @Override
    public void visit(EndGame event) {

    }

    @Override
    public void visit(StartGame event) {

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
