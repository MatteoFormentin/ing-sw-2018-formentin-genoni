package it.polimi.se2018.view.gui;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;
import javafx.application.Application;
import javafx.application.Platform;

import static it.polimi.se2018.view.gui.GuiReceiver.launchGui;
import static it.polimi.se2018.view.gui.gamestage.GuiGame.getGuiGame;

public class GuiInstance implements UIInterface {
    private static GuiInstance instance;
    private ClientController client;

    private GuiInstance() {
        launchGui();
    }

    public static GuiInstance getGuiInstance(){
        if(instance==null) instance= new GuiInstance();
        return instance;
    }

    public void setClient(ClientController client) {
        this.client = client;
    }

    public ClientController getClient() {
        return client;
    }

    @Override
    public void showMessage(EventView eventView) {
        Platform.runLater(() -> {
                    try {
                        getGuiGame().showMessage(eventView);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
        );
    }

    public void sendEventToNetwork(EventController event) {
        client.sendEventToController(event);
    }
}
