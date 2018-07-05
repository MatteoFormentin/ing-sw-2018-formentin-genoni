package it.polimi.se2018.view.gui;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.alternative_network.client.ClientFactory;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.gui.classes_database.PlayerOnline;
import it.polimi.se2018.view.gui.stage.WaitGame;

import java.util.LinkedList;

import static it.polimi.se2018.view.gui.GuiMain.launchGui;
import static it.polimi.se2018.view.gui.gamestage.GuiGame.getGuiGame;

/**
 * the only class that link the Network to the Gui and vice versa.
 *
 * @author Luca Genoni
 */
public class ControllerGUI implements UIInterface,ViewVisitor,ViewControllerVisitor {

    //field for connect to server
    private static ControllerGUI instance;
    private static ClientController client;
    private ClientFactory factoryInstance;
    private AbstractClient2 client2;

    //field for handle the gui
    private WaitGame waitGame;


    //field
    private LinkedList<PlayerOnline> players;

    private ControllerGUI(){

    }

    private ControllerGUI(ClientController client) {
        this.client = client;
    }

    public ClientFactory getFactoryInstance() {
        return factoryInstance;
    }

    public void setFactoryInstance(ClientFactory factoryInstance) {
        this.factoryInstance = factoryInstance;
    }

    public AbstractClient2 getClient2() {
        return client2;
    }

    public void setClient2(AbstractClient2 client2) {
        this.client2 = client2;
    }

    public static void main(String[] args) {
        getSingletonGUIInstance();
        instance.setFactoryInstance(ClientFactory.getClientFactory());
        instance.startGui();
    }

    public static void createGuiInstance() {
        if (instance == null) instance = new ControllerGUI(client);
    }

    /**
     * Method for create only one ControllerGUI
     *
     * @param client for the connection
     */
    public static void createGuiInstance(ClientController client) {
        if (instance == null) instance = new ControllerGUI(client);
    }

    /**
     * Get the Instance of the Gui, if null the application is off
     *
     * @return the instance of the bridge between the JavaFx Application and the Client
     */
    public static ControllerGUI getGuiInstance() {
        return instance;
    }

    /**
     * Singleton method for build the Gui
     *
     * @return ControllerGUI
     */
    public static ControllerGUI getSingletonGUIInstance(){
        if(instance==null) instance = new ControllerGUI();
        return instance;
    }

    /**
     * Get the Instance of the Gui, if null the application is off
     *
     * @return the client Controller for init the connection.
     */
    public ClientController getClient() {
        return client;
    }


    /**
     * Start the single Thread of JavaFx Application
     */
    public void startGui() {
        launchGui();
    }

    public WaitGame getWaitGame() {
        return waitGame;
    }

    public void disconnect(){
        if(factoryInstance==null) {
            //TODO mettere la disconnessione volontaria
            //client.disconnect();
        }
        else {
            client2.shutDownClient2();
        }
    }

    //*************************************************************************************
    //*************************************************************************************
    //                                  UI Interface method
    //*************************************************************************************
    //*************************************************************************************

    /**
     * forward the message to the gui
     *
     * @param eventView EventView for the gui
     */
    @Override
    public void showEventView(EventView eventView) {
        getGuiGame().showEventView(eventView);
    }

    /**
     * Method for send to the server the Event Controller
     *
     * @param eventController to send to the server
     */
    @Override
    public void sendEventToNetwork(EventController eventController) {
        if(factoryInstance==null) client.sendEventToController(eventController);
        else {
            try {
                client2.sendEventToController2(eventController);
            }catch (ConnectionProblemException ex){
                restartConnection(ex.getMessage());
            }
        }
    }

    /**
     * notify the Gui of the connection down and ask if need to be restart it
     *
     * @param cause the cause of the connection error
     */

    @Override
    public void restartConnection(String cause) {
        getGuiGame().restartConnection(cause);
    }

    /**
     * Generic println
     *
     * @param error the message
     */
    @Override
    public void errPrintln(String error){
        System.err.println();
        System.err.println(error);
        System.err.println();
    }

    //*************************************************************************************
    //*************************************************************************************
    //                                  View Visitor
    //*************************************************************************************
    //*************************************************************************************
    @Override
    public void visit(EventViewFromController eventView) {
        eventView.acceptGeneric(this);
    }

    @Override
    public void visit(EventViewFromModel eventView) {
        eventView.acceptGeneric(this);
    }


    //*************************************************************************************
    //*************************************************************************************
    //                           Visitor Event From Controller
    //*************************************************************************************
    //*************************************************************************************

    @Override
    public void visit(StartGame event) {

    }

    @Override
    public void visit(ShowAllCards event) {

    }

    @Override
    public void visit(SelectInitialWindowPatternCard event) {

    }

    @Override
    public void visit(InitialEnded event) {

    }

    @Override
    public void visit(MessageError event) {

    }

    @Override
    public void visit(MessageOk event) {

    }

    @Override
    public void visit(StartPlayerTurn event) {

    }

    @Override
    public void visit(SelectCellOfWindow event) {

    }

    @Override
    public void visit(SelectDiceFromDraftPool event) {

    }

    @Override
    public void visit(SelectToolCard event) {

    }

    @Override
    public void visit(MoveTimeoutExpired event) {

    }

    @Override
    public void visit(WaitYourTurn event) {

    }

    @Override
    public void visit(SelectDiceFromRoundTrack event) {

    }

    @Override
    public void visit(SelectValueDice event) {

    }

    @Override
    public void visit(SelectIncrementOrDecreaseDice event) {

    }

    @Override
    public void visit(EndGame event) {

    }

}
