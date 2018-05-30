package it.polimi.se2018.list_event.event_received_by_view;


import java.io.Serializable;

/**
 * Abstract void class for the EventController in a game, implements Serializable.
 * Every EventController is produced by a player and belong to a specific game board
 * <p>
 * SENDED FROM CONTROLLER TO VIEW
 *
 * @author Luca Genoni
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
