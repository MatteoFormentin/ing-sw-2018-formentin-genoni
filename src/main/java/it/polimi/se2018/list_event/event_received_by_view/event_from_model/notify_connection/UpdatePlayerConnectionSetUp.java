package it.polimi.se2018.list_event.event_received_by_view.event_from_model.notify_connection;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;

/**
 * Extends EventClient, updates the initial length of the RoundTrack
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdatePlayerConnectionSetUp extends EventClientFromModel {

    private String nickname;
    private boolean connected;

    public UpdatePlayerConnectionSetUp(String nickname, boolean connected) {
        this.nickname = nickname;
        this.connected = connected;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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