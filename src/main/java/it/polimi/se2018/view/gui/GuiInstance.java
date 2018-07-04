package it.polimi.se2018.view.gui;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.alternative_network.client.ClientFactory;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.Client;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.cli.CliController;

import static it.polimi.se2018.view.gui.GuiMain.launchGui;
import static it.polimi.se2018.view.gui.gamestage.GuiGame.getGuiGame;

/**
 * the only class that link the Network to the Gui and vice versa.
 *
 * @author Luca Genoni
 */
public class GuiInstance implements UIInterface {
    private static GuiInstance instance;
    private static ClientController client;
    private ClientFactory factoryInstance;
    private AbstractClient2 client2;

    private GuiInstance(ClientController client) {
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
        createGuiInstance();
        instance.setFactoryInstance(ClientFactory.getClientFactory());
        instance.startGui();
    }

    public static void createGuiInstance() {
        if (instance == null) instance = new GuiInstance(client);
    }

    /**
     * Method for create only one GuiInstance
     *
     * @param client for the connection
     */
    public static void createGuiInstance(ClientController client) {
        if (instance == null) instance = new GuiInstance(client);
    }

    /**
     * Get the Instance of the Gui, if null the application is off
     *
     * @return the instance of the bridge between the JavaFx Application and the Client
     */
    public static GuiInstance getGuiInstance() {
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

    /**
     * forward the message to the gui
     *
     * @param eventView EventView for the gui
     */
    @Override
    public void showMessage(EventView eventView) {
        getGuiGame().showMessage(eventView);
    }

    @Override
    public void restartConnectionDuringGame(String cause) {
        getGuiGame().restartConnectionDuringGame(cause);
    }

    @Override
    public void errPrintln(String error){
        System.err.println();
        System.err.println(error);
        System.err.println();
    }
    /**
     * Method for send to the server the Event Controller
     *
     * @param event to send to the server
     */
    public void sendEventToNetwork(EventController event) {
        if(factoryInstance==null) client.sendEventToController(event);
        else {
            try {
                client2.sendEventToController2(event);
            }catch (ConnectionProblemException ex){
                restartConnectionDuringGame(ex.getMessage());
            }
        }
    }
}
