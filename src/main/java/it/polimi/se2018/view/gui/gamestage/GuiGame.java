package it.polimi.se2018.view.gui.gamestage;

import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.GuiReceiver;
import it.polimi.se2018.view.gui.stage.AlertMessage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GuiGame implements UIInterface,ViewVisitor {

    private static BorderPane box;
    private boolean change;
    private static Text confirmMessage;


    public Scene displayWait(){
        //scene wait
        confirmMessage= new Text();
        confirmMessage.setText("Aspetta che si colleghino gli altri giocatori");
        //   confirmMessage.setText("test");

        box = new BorderPane();
        box.setCenter(confirmMessage);
        Scene scene = new Scene(box, 400, 200, Color.BLACK);
        scene.setCursor(Cursor.WAIT);
        return scene;
    }
    @Override
    public void visit(EndGame event) {
        confirmMessage= new Text("miracolo");
        box.setCenter(confirmMessage);
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

    @Override
    public void showMessage(EventView EventView) {
        EventView.accept(this);
    }
}


