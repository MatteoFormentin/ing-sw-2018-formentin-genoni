package it.polimi.se2018.view.gui;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.alternative_network.client.ClientFactory;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.AskLogin;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.ConnectionDown;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.LoginResponse;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.gamestage.GuiGame;
import it.polimi.se2018.view.gui.stage.*;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * the only class that link the Network to the Gui and vice versa.
 *
 * @author Luca Genoni
 */
public class ControllerGUI implements UIInterface, ViewVisitor, ViewControllerVisitor {

    //field for connect to server
    private static ControllerGUI instanceControllerGUI;
    private ClientFactory clientFactory;

    private int myId;

    //field for handle the gui
    private Stage primaryStage;
    private Stage stage2;
    private SetUpConnection connection;
    private Login login;
    private WaitGame waitGame;
    private GuiGame game;

    public static ControllerGUI getInstanceControllerGUI() {
        return instanceControllerGUI;
    }

    private static void setInstanceControllerGUI(ControllerGUI controllerGUI){
        instanceControllerGUI=controllerGUI;
    }

    ControllerGUI(Stage primaryStage) {
        setInstanceControllerGUI(this);
        this.primaryStage=primaryStage;
        clientFactory= ClientFactory.getClientFactory();
        connection = new SetUpConnection(clientFactory);
        login = new Login();
        waitGame=new WaitGame("Aspetta che tutti i giocatori si siano collegati");
        stage2 = new Stage(StageStyle.UTILITY);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initOwner(primaryStage);
        stage2.setResizable(false);
        stage2.setOnCloseRequest(e->{
            ConfirmBox ask = new ConfirmBox("Sei dicuro di volerti disconnettere?");
            clientFactory.getAbstractClient().shutDownClient2();
        });
    }

    void intiConnection(Stage stage){
        primaryStage=stage;
        connection.display(stage2);
        if(clientFactory.getAbstractClient()!=null) clientFactory.getAbstractClient().connectToServer2();
    }

    public void disconnect() {
        stage2.close();
        if(clientFactory.getAbstractClient()!=null)clientFactory.getAbstractClient().shutDownClient2();
    }

    //*************************************************************************************
    //*************************************************************************************
    //                                  UI Interface method
    //*************************************************************************************
    //*************************************************************************************

    /**
     * forward the message to the gui
     *
     * @param eventClient EventClient for the gui
     */
    @Override
    public void showEventView(EventClient eventClient) {
        AnsiConsole.out.println(ansi().fg(GREEN).a("Ricevuto: "+eventClient));
        eventClient.acceptGeneric(this);
    }
    /**
     * Method for send to the server the Event Controller
     *
     * @param eventController to send to the server
     */
    @Override
    public void sendEventToNetwork(EventController eventController) {
        AnsiConsole.out.println(ansi().fg(RED).a("Inviati al network: "+eventController));
        eventController.setPlayerId(myId);
        clientFactory.getAbstractClient().sendEventToController2(eventController);
    }

    //*************************************************************************************
    //*************************************************************************************
    //                                  View Visitor
    //*************************************************************************************
    //*************************************************************************************
    @Override
    public void visit(EventClientFromController eventView) {
        Platform.runLater(()-> eventView.acceptControllerEvent(this));
    }

    @Override
    public void visit(EventClientFromModel eventView) {
        Platform.runLater(()-> eventView.acceptModelEvent(game));
    }


    //*************************************************************************************
    //*************************************************************************************
    //                           Visitor Event From Controller
    //*************************************************************************************
    //*************************************************************************************

    @Override
    public void visit(LoginResponse event) {
        stage2.close();
        if(event.isLoginSuccessFull()){
            game =new GuiGame(stage2,waitGame);
            waitGame.displayWaiting(stage2);
        }else{
            new AlertMessage(stage2).displayMessage(event.getCause());
        }
    }

    @Override
    public void visit(ConnectionDown event) {
        stage2.close();
        new AlertMessage(stage2).displayMessage(event.getCause());
    }

    @Override
    public void visit(AskLogin event) {
        stage2.close();
        LoginRequest packet = login.display(stage2);
        if(packet==null) clientFactory.getAbstractClient().shutDownClient2();
        else {
            clientFactory.getAbstractClient().sendEventToController2(packet);
        }
    }

    @Override
    public void visit(StartGame event) {
        myId=event.getPlayerId();
    }

    @Override
    public void visit(ShowAllCards event) {
        game.activeShowAllCards();
    }

    @Override
    public void visit(SelectInitialWindowPatternCard event) {
        game.activeSelectInitialWindowPatternCard();
    }

    @Override
    public void visit(InitialEnded event) {
        game.goToBoard();
    }

    @Override
    public void visit(MessageError event) {
        game.activeMessageError(event);
    }

    @Override
    public void visit(MessageOk event) {
        game.activeMessageOk(event);
    }

    @Override
    public void visit(StartPlayerTurn event) {
        game.ableTurn();
    }

    @Override
    public void visit(SelectCellOfWindow event) {
        game.activeCellOfWindow();
    }

    @Override
    public void visit(SelectDiceFromDraftPool event) {
        game.activeSelectDiceFromDraftPool();
    }

    @Override
    public void visit(SelectToolCard event) {
        game.activeSelectToolCard();
    }

    @Override
    public void visit(MoveTimeoutExpired event) {
        game.timeOver();
    }

    @Override
    public void visit(WaitYourTurn event) {
        game.notYourTurn(event);
    }

    @Override
    public void visit(SelectDiceFromRoundTrack event) {
        game.activeDiceFromRoundTrack();

    }

    @Override
    public void visit(SelectValueDice event) {
        game.activeSelectValueDice();
    }

    @Override
    public void visit(SelectIncrementOrDecreaseDice event) {
        game.activeSelectIncrementOrDecreaseDice();
    }

    @Override
    public void visit(EndGame event) {

    }

}
