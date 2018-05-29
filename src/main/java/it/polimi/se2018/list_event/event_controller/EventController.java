package it.polimi.se2018.list_event.event_controller;


import java.io.Serializable;

/**
 * Abstract void class for the EventView in a game, implements Serializable.
 * Every EventView is produced by a player and belong to a specific game board
 * <p>
 * SENDED FROM CONTROLLER TO VIEW
 *
 * @author Luca Genoni
 */
public abstract class EventController implements Serializable {
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public abstract void accept(VisitorEventFromController visitor);
}
