package it.polimi.se2018.view.gui;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;

import static it.polimi.se2018.view.gui.GuiReceiver.launchGui;
import static it.polimi.se2018.view.gui.gamestage.GuiGame.getGuiGame;

/**
 * the only class that link the Network to the Gui and vice versa.
 *
 * @author Luca Genoni
 */
public class GuiInstance implements UIInterface {
    private static GuiInstance instance;
    private ClientController client;

    private GuiInstance(ClientController client) {
        this.client = client;
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

    /**
     * Method for send to the server the Event Controller
     *
     * @param event to send to the server
     */
    public void sendEventToNetwork(EventController event) {
        client.sendEventToController(event);
    }
}
