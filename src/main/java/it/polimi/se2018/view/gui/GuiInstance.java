package it.polimi.se2018.view.gui;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import static it.polimi.se2018.view.gui.GuiReceiver.launchGui;
import static it.polimi.se2018.view.gui.gamestage.GuiGame.getGuiGame;

/**
 * the only class that the network see
 */
public class GuiInstance implements UIInterface {
    private static GuiInstance instance;
    private ClientController client;

    private GuiInstance(ClientController client) {
        this.client = client;
    }

    public static GuiInstance getGuiInstance() {
        return instance;
    }

    public ClientController getClient() {
        return client;
    }

    public static void createGuiInstance(ClientController client) {
        if (instance == null) instance = new GuiInstance(client);
    }

    public void startGui() {
        launchGui();
    }

    @Override
    public void showMessage(EventView eventView) {
        getGuiGame().showMessage(eventView);

    }

    public void sendEventToNetwork(EventController event) {
        client.sendEventToController(event);
    }
}
