package it.polimi.se2018.list_event.event_received_by_view;

import java.io.Serializable;

/**
 * Abstract void class for the event read by the view in a game, implements Serializable.
 * EventViews are produced by the controller and the model.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public abstract class EventView implements Serializable {
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public abstract void accept(ViewVisitor visitor);

}
