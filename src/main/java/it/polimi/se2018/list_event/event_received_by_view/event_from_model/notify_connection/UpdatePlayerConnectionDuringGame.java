package it.polimi.se2018.list_event.event_received_by_view.event_from_model.notify_connection;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;

/**
 * Extends EventClient, updates the player disconnected during the game
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdatePlayerConnectionDuringGame extends EventClientFromModel {

    private String name;
    private boolean connected;

    public UpdatePlayerConnectionDuringGame(String name, boolean connected) {
        this.name = name;
        this.connected = connected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
